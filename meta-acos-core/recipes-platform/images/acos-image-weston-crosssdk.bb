require acos-image-weston.bb

SUMMARY = "Cross SDK of minimal ACOS Distribution for core profile"
DESCRIPTION = "SDK image for ACOS core distribution. \
It includes the full toolchain, plus developement headers and libraries \
to form a standalone cross SDK."
LICENSE = "MIT"

inherit acos-crosssdk
