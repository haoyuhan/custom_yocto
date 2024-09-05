DESCRIPTION = "for the vendor's packagegroup"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

DEPENDS:${PN} = "\
        ${@bb.utils.contains('DISTRO_FEATURES', 'llce-can', ' linux-firmware-llce-can', '', d)} \
        ${@bb.utils.contains('DISTRO_FEATURES', 'optee', ' optee-client optee-examples optee-test ', '', d)} \
        ${@bb.utils.contains('DISTRO_FEATURES', 'pfe', ' pfe-firmware pfe', '', d)} \
        ${@bb.utils.contains('DISTRO_FEATURES', 'hse', ' hse-firmware', '', d)} \
        "

