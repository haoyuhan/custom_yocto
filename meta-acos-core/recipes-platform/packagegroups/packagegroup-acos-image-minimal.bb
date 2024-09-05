DESCRIPTION = "The minimal set of packages required by ACOS"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-acos-image-minimal \
    profile-acos-minimal \
    packagegroup-vendor-${MACHINE} \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-acos-core-boot \
    packagegroup-acos-core-connectivity \
    packagegroup-vendor-${MACHINE} \
    base-files \
    repart \
    "
RDEPENDS:profile-acos-minimal = "${PN}"
