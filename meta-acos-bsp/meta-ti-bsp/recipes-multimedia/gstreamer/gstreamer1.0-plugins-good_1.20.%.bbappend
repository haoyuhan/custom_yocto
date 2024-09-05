FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://0001-Adding-support-for-raw10-raw12-and-raw16-bayer-formats.patch \
    file://0002-Adding-support-for-bayer-formats-with-IR-component.patch \
    file://0004-v4l2-Give-preference-to-contiguous-format-if-support.patch \
"

PR:append = ".arago2"
