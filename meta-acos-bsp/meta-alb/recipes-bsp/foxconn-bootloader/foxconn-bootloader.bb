SUMMARY = "FOXCONN Bootloader"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

inherit deploy

#FOXCONN_BINARIES_DIR ?= "."
#FOXCONN_BOOTLOADER_DIR ?= "${FOXCONN_BINARIES_DIR}"
FOXCONN_BOOTLOADER_BIN ?= "boot-loader"

#SRC_URI = "file://${FOXCONN_BOOTLOADER_DIR}/${FOXCONN_BOOTLOADER_BIN}"
SRC_URI += " \
           file://${FOXCONN_BOOTLOADER_BIN} \
           "

# tell yocto not to strip our binary
INHIBIT_PACKAGE_STRIP = "1"
S = "${WORKDIR}"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}/boot
	install -m 0644 "${S}/${FOXCONN_BOOTLOADER_BIN}" ${D}/boot
}

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 0644 ${D}/boot/${FOXCONN_BOOTLOADER_BIN} ${DEPLOYDIR}/${FOXCONN_BOOTLOADER_BIN}
}

addtask do_deploy after do_install

FILES:${PN} += "/boot/${FOXCONN_BOOTLOADER_BIN}"
