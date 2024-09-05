SUMMARY = "The middleware for ACOS IVI profile"
DESCRIPTION = "The base set of packages required for a ACOS IVI Distribution"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-acos-profile-graphical \
    profile-graphical \
"

RDEPENDS:${PN} += "\
    packagegroup-acos-image-minimal \
    packagegroup-acos-graphical-compositor \
"

RDEPENDS:profile-graphical = "${PN}"
