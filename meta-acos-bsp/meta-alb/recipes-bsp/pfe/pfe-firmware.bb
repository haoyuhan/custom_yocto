SUMMARY = "PFE FIRMWARE"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

inherit deploy

#FOXCONN_BINARIES_DIR ?= "."
#FOXCONN_BOOTLOADER_DIR ?= "${FOXCONN_BINARIES_DIR}"
PFE_FIRMWARE ?= "s32g_pfe_class.fw"

SRC_URI += " \
           file://${PFE_FIRMWARE} \
           "

# tell yocto not to strip our binary
INHIBIT_PACKAGE_STRIP = "1"
S = "${WORKDIR}"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}/boot
	install -m 0644 "${S}/${PFE_FIRMWARE}" ${D}/boot
}

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 0644 ${D}/boot/${PFE_FIRMWARE} ${DEPLOYDIR}/${PFE_FIRMWARE}
}

addtask do_deploy after do_install

FILES:${PN} += "/boot/${PFE_FIRMWARE}"


