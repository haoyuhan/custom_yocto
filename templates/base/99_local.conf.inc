########################
# AC DISTRO VARIABLES #
########################

# ARM 32bit 'medium' compiler (armv7thf instead of armv7ve)
# (only use for CPUs <= cortex-a9 / w/o virt extension - e.g. cortex-a8)
# ACOS_FEATURES:append = " ac-medium-arm-compiler"


######################
# AC USER VARIABLES #
######################

# Add additional IMAGE_FSTYPES here on-top of the default ext4.xz and tar.xz
#AC_EXTRA_IMAGE_FSTYPES = ""

# Additional free disk space created in the image in Kbytes.
#IMAGE_ROOTFS_EXTRA_SPACE="524288"


# meta-acos-profile-graphical-qt5/recipes-qt/qt5/qtwayland_%.bbappend
# This defines the QT integration. We default to xdg-shell
# AC_DEFAULT_WM_SHELL ?= "xdg-shell"
# you can redefine it using this variable to e.g. use ivi-shell
# but be careful that this needs further changes to work !
#AC_DEFAULT_WM_SHELL = ""
