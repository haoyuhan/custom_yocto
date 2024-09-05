DESCRIPTION = "GStreamer elements to use the multimedia accelerators available on some TI parts"
LICENSE = "LGPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=fbc093901857fcd118f065f900982c24"

require gstreamer1.0-plugins-ti.inc

inherit features_check

REQUIRED_MACHINE_FEATURES = "mmip"

PR = "${INC_PR}.31"
SRCREV = "7e3938f0854be99583f8b204f600907ea9e6471d"

BRANCH ?= "master"

SRC_URI = " \
    git://git.ti.com/git/glsdk/gst-plugin-ducati.git;protocol=https;branch=${BRANCH} \
    file://0001-gstducati-configure.ac-stop-using-export-symbols-reg.patch \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
