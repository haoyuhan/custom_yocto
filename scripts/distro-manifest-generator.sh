#!/usr/bin/env bash

################################################################################
#
# The MIT License (MIT)
#
# Copyright (c) 2018 Stéphane Desneux <sdx@iot.bzh>
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#
################################################################################

mode=deploy
manifest=
verbose=0
format=bash
sourcefile=
timestamp="$(date -u +%Y%m%d_%H%M%S_%Z)"

function info() { echo "$@" >&2; }
function error() { echo "$BASH_SOURCE: $@" >&2; }
function out() { echo -n "$@"; }
function out_object() {
	# expected stdin stream is:
	# --------------
	# key
	# value
	# key
	# value
	# ...
	# --------------
	local sep=""
	local k
	case $format in
		bash)
			while read x; do
				[[ -z "$k" ]] && { k="$x"; continue; }
				out "$sep${k}="
				out_value "$x"
				sep=$'\n'
				k=
			done
			out "$sep"
			;;
		json)
			out "{"
			while read x; do
				[[ -z "$k" ]] && { k="$x"; continue; }
				out "$sep\"${k}\":"
				out_value "$x"
				sep=","
				k=
			done
			out "}"
			;;
	esac
}

function out_array() {
	# expected stdin stream is:
	# --------------
	# value
	# value
	# ...
	# --------------
	local sep=""
	case $format in
		bash)
			while read x; do
				out "$sep"
				out_value "$x"
				sep=" "
			done
			;;
		json)
			out "["
			while read x; do
				out $sep
				out_value "$x"
				sep=","
			done
			out "]"
			;;
	esac
}

function out_value() {
	# string
	# number
	# object
	# array
	# 'true'
	# 'false'
	# 'null'

	x=$1

	# litterals
	if [[ "$x" =~ ^(true|false|null)$ ]]; then
		out "$x"
	# number
	elif [[ "$x" =~ ^[+-]?[0-9]+(\.[0-9]+)?$ ]]; then
		out "$x"
	# object
	elif [[ "$x" =~ ^\{.*\}$ ]]; then
		out "$x"
	# array
	elif [[ "$x" =~ ^\[.*\]$ ]]; then
		out "$x"
	# string
	else
		out "\"$(sed 's/\("\)/\\\1/g' <<<$x)\""
	fi
}

function out_comment() {
	case $format in
		bash)
			[[ "$verbose" == 1 ]] && echo "# $@" || true
			;;
		json)
			;;
	esac
}

function _getgitmanifest() {
	# this function takes the setup.manifest generated by setup script and uses DIST_METADIR
	# to analyze git repos

	local manifest=$1 mode=$2
	[[ -f $manifest ]] && source $manifest || { error "Invalid setup manifest '$manifest'"; return 1; }
	[[ ! -d "$DIST_METADIR" ]] && {
		error "Invalid meta directory. Check variable DIST_METADIR in manifest file '$manifest'."
		error "$BASH_SOURCE: Also, check directory '$DIST_METADIR'."
		return 2
	}
	local GIT=$(which git) REALPATH=$(which realpath)
	[[ ! -x $GIT ]] && { error "$BASH_SOURCE: Unable to find git command in $PATH."; return 3; }
	[[ ! -x $REALPATH ]] && { error "$BASH_SOURCE: Unable to find realpath command in $PATH."; return 4; }

	local gitrepo gitrev metagitdir sep=""
	DIST_LAYERS=""
	for metagitdir in $(find $DIST_METADIR -maxdepth 2 -type d \( -not -path '*/.*' \) -exec test -d "{}/.git" \; -print -prune); do
		gitrepo=$($REALPATH -Ls $metagitdir --relative-to=$DIST_METADIR)
		pushd $DIST_METADIR/$gitrepo &>/dev/null && {
			gitrev=$( { $GIT describe --long --dirty --always 2>/dev/null || echo "unknown_revision"; } | tr ' \t' '__' )
			popd &>/dev/null
		} || {
			gitrev=unknown
		}
		DIST_LAYERS="${DIST_LAYERS}${sep}${gitrepo}:${gitrev}"
		sep=" "
	done

	# layers checksum
	DIST_LAYERS_MD5=$(echo $DIST_LAYERS|md5sum -|awk '{print $1;}')

	# in json, transform layers in an object, features in array
	[[ "$format" == "json" ]] && {
		DIST_FEATURES=$(for x in $DIST_FEATURES; do
			echo $x
		done | out_array)
		DIST_LAYERS=$(for x in $DIST_LAYERS; do
			echo ${x%%:*}
			echo ${x#*:}
		done | out_object)
	}


	# compute build hash
	DIST_BUILD_HASH="F${DIST_FEATURES_MD5:0:8}-L${DIST_LAYERS_MD5:0:8}"
	DIST_BUILD_ID="${DIST_DISTRO_NAME}-${DIST_MACHINE}-F${DIST_FEATURES_MD5:0:8}-L${DIST_LAYERS_MD5:0:8}"


	# compute setup manifest path and build TS
	DIST_SETUP_MANIFEST="$($REALPATH $manifest)"

	# Manifest build timestamp
	DIST_BUILD_TS="$timestamp"

	# build topic from setup topic
	DIST_BUILD_TOPIC="${DIST_SETUP_TOPIC}"

	# what to retain from setup manifest?
	# to generate the full list: cat setup.manifest  | grep = | cut -f1 -d"=" | awk '{printf("%s ",$1);}'
	declare -A SETUP_VARS
	SETUP_VARS[deploy]="DIST_MACHINE DIST_FEATURES DIST_FEATURES_MD5 DIST_BUILD_HOST DIST_BUILD_OS DIST_SETUP_TS"
	SETUP_VARS[target]="DIST_MACHINE DIST_FEATURES"
	SETUP_VARS[sdk]="DIST_MACHINE DIST_FEATURES"

	# extra vars not coming from setup.manifest but generated here
	declare -A EXTRA_VARS
	EXTRA_VARS[deploy]="DIST_SETUP_MANIFEST DIST_BUILD_TS DIST_LAYERS DIST_LAYERS_MD5 DIST_BUILD_HASH DIST_BUILD_ID DIST_BUILD_TOPIC"
	EXTRA_VARS[target]="DIST_LAYERS DIST_BUILD_HASH DIST_BUILD_ID DIST_BUILD_TS DIST_BUILD_TOPIC"
	EXTRA_VARS[sdk]="DIST_LAYERS DIST_BUILD_HASH DIST_BUILD_ID DIST_BUILD_TS DIST_BUILD_TOPIC"

	# BITBAKE_VARS may be defined from external file to source (--source arg)
	# this is used to dump extra vars from inside bitbake recipe

	{ for x in ${SETUP_VARS[$mode]} ${EXTRA_VARS[$mode]} ${BITBAKE_VARS[$mode]}; do
		k=$x
		[[ "$format" == "json" ]] && {
			k=${k#DIST_} # remove prefix
			k=${k,,*} # to lower case
		}
		echo $k
		echo ${!x}
	done } | out_object
}

function getmanifest() {
	local rc=0
	out_comment "DISTRO BUILD MANIFEST"
	out_comment

	# add layers manifest
	out_comment "----- this fragment has been generated by $BASH_SOURCE"
	_getgitmanifest $1 $2 || rc=$?
	out_comment "------------ end of $BASH_SOURCE fragment --------"

	return $rc
}

function __usage() {
	cat <<EOF >&2
Usage: $BASH_SOURCE [-v|--verbose] [-f|--format <fmt>] [-t|--timestamp <value>] [-m|--mode <mode>] [-s|--source <file>] <setup_manifest_file>
   Options:
      -v|--verbose: generate comments in the output file
      -s|--source: extra file to source (get extra variables generated from bitbake recipe)
      -t|--timestamp: set build timestamp (default: current date - may not be the same ts as bitbake)
      -f|--format: specify output format: 'bash' or 'json'
      -m|--mode: specify the destination for the generated manifest
         'deploy' : for the tmp/deploy/images/* directories
         'target' : for the manifest to be installed inside a target image
         'sdk'    : for the manifest to be installed inside the SDK

   <setup_manifest_file> is the input manifest generated from setup script
EOF
}

set -e

tmp=$(getopt -o h,v,m:,f:,t:,s: --long help,verbose,mode:,format:,timestamp:,source: -n "$BASH_SOURCE" -- "$@") || {
	error "Invalid arguments."
	__usage
	exit 1
}
eval set -- $tmp

while true; do
	case "$1" in
		-h|--help) __usage; exit 0;;
		-v|--verbose) verbose=1; shift ;;
		-f|--format) format=$2; shift 2;;
		-m|--mode) mode=$2; shift 2;;
		-t|--timestamp) timestamp=$2; shift 2;;
		-s|--source) sourcefile=$2; shift 2;;
		--) shift; break;;
		*) fatal "Internal error";;
	esac
done

manifest=$1
shift
[[ ! -f "$manifest" ]] && { __usage; exit 1; }

case $mode in
	deploy|target|sdk) ;;
	*) error "Invalid mode specified. Allowed modes are: 'deploy', 'target', 'sdk'"; __usage; exit 42;;
esac

case $format in
	bash|json) ;;
	*) error "Invalid format specified. Allowed formats are 'json' or 'bash'"; __usage; exit 43;;
esac

info "Generating manifest: mode=$mode format=$format manifest=$manifest"
[[ -f "$sourcefile" ]] && {
	info "Sourcing file $sourcefile"
	. $sourcefile
	# this may define extra vars: to be taken into account BITBAKE_VARS must be defined
}

[[ "$format" == "json" ]] && {
	# if jq is present, use it to format json output
	jq=$(which jq || true)
	[[ -n "$jq" ]] && {
		getmanifest $manifest $mode | $jq ""
		exit ${PIPESTATUS[0]}
	}
}

getmanifest $manifest $mode

