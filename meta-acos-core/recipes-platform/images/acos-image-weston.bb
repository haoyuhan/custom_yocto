SUMMARY = "A very basic Wayland image with a terminal"
LICENSE = "MIT"

require acos-image-minimal.bb

inherit features_check

REQUIRED_DISTRO_FEATURES = "wayland"

SYSTEMD_DEFAULT_TARGET = "graphical.target"

IMAGE_INSTALL += " \
    packagegroup-acos-graphical-weston \
    ${@bb.utils.contains('DISTRO_FEATURES', 'acos-devel', 'weston-examples', '', d)} \
"
