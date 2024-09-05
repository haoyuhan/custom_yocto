DESCRIPTION = "The minimal set of packages required for the ACOS compositor"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS:${PN} += " \
    acos-compositor \
    acos-compositor-init \
"
