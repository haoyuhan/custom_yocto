#!/bin/bash

################################################################################
#
# The MIT License (MIT)
#
# Copyright (c) 2024 tom <tom.han@autocore.ai>
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


SOURCED=0
if [ -n "$ZSH_EVAL_CONTEXT" ]; then 
	[[ $ZSH_EVAL_CONTEXT =~ :file$ ]] && { SOURCED=1; SOURCEDIR=$(cd $(dirname -- $0) && pwd -P); }
elif [ -n "$KSH_VERSION" ]; then
	[[ "$(cd $(dirname -- $0) && pwd -P)/$(basename -- $0)" != "$(cd $(dirname -- ${.sh.file}) && pwd -P)/$(basename -- ${.sh.file})" ]] && { SOURCED=1; SOURCEDIR=$(cd $(dirname -- ${.sh.file}) && pwd -P); }
elif [ -n "$BASH_VERSION" ]; then
	[[ $0 != "$BASH_SOURCE" ]] && { SOURCED=1; SOURCEDIR=$(cd $(dirname -- $BASH_SOURCE) && pwd -P); }
fi

if [ $SOURCED -ne 1 ]; then
	unset SOURCED
	unset SOURCEDIR
	echo "Error: this script needs to be sourced in a supported shell" >&2
	echo "Please check that the current shell is bash, zsh or ksh and run this script as '. $0 <args>'" >&2
	return 1
else
	unset SOURCED
	cat <<'EOF' >&2
 ------------------------------------------------------------------------------
| welcome to ...                                                      |
|  
           _    _  _______  ____    _____  ____   _____   ______    ____    _____ 
     /\   | |  | ||__   __|/ __ \  / ____|/ __ \ |  __ \ |  ____|  / __ \  / ____|
    /  \  | |  | |   | |  | |  | || |    | |  | || |__) || |__    | |  | || (___  
   / /\ \ | |  | |   | |  | |  | || |    | |  | ||  _  / |  __|   | |  | | \___ \ 
  / ____ \| |__| |   | |  | |__| || |____| |__| || | \ \ | |____  | |__| | ____) |
 /_/    \_\\____/    |_|   \____/  \_____|\____/ |_|  \_\|______|  \____/ |_____/ 
                                                                                  
                                                                                  
                                                                            |
| Please select machine & features first ....
 ------------------------------------------------------------------------------
EOF
fi

machines_list=("raspberrypi4-64 " "s32g274ardb2 " "j721e-evm " "j784s4-evm")

features_list=("acos-devel" " acos-selinux" " acos-gpt")
s32_product_list=("acos-eea " "acos-agile" "default")
soc_family=""
choices_features=()
choices_machine=""
product_list=()
set_clear="\033[0m"
fg_red="\033[31m"
fg_green="\033[32m"

function list_machine()
{
	echo -e "Please select an machine numer: \n"
	for i in "${!machines_list[@]}"; do
		printf "%s\t%s\n" "$i" "${machines_list[$i]}"
	done
    
	echo -e "\n"
	read -p "selected machine number: " -r choices

        echo -e "\n"
	choices_machine=${machines_list[$choices]}

        echo -e "$fg_green the machine you select: $fg_red $choices_machine $set_clear"
        echo -e "\n"
        echo -e "--------------------------------------\n"

}


function list_product() 
{
  if [ $choices_machine  == "s32g274ardb2" ]; then
     product_list=("${s32_product_list[@]}")  # Use double quotes for variable expansion
  else
    echo -e "$choices_machine product not supported now, will support future\n"
    return
  fi

  for i in "${!product_list[@]}"; do
    printf "%s\t%s\n" "$i" "${product_list[$i]}"
  done

  echo -e "\n"
  read -p "selected product number: " -r choices_product

  echo -e "\n"
  echo -e "$fg_green the product you select: $fg_red ${product_list[$choices_product]} $set_clear"
  echo -e "\n"
}


function list_features()
{
	echo -e "Please select features numbers: \n"
	for i in "${!features_list[@]}"; do
              printf "%s\t%s\n" "$i" "${features_list[$i]}"
        done
	echo -e "\n"
	read -p "selected features numbers:"  -r -a choices

    for i in "${choices[@]}";do
      choices_features+=${features_list[$i]}
    done
    echo -e "\n"
    echo -e "$fg_green the features :  $fg_red $choices_features $set_clear"
    echo -e "\n"
}


list_machine
#set_soc_family
list_product
list_features


echo -e "--------------------------------------\n"

sleep 5s

if [ -z "$product_list" ];then
   . $SOURCEDIR/sources/meta-acos-custom/scripts/acsetup.sh -m $choices_machine  $choices_features "$@"
else
   . $SOURCEDIR/sources/meta-acos-custom/scripts/acsetup.sh -m $choices_machine -p ${product_list[$choices_product]}   $choices_features "$@"
fi

rc=$?
unset SOURCEDIR
return $rc
