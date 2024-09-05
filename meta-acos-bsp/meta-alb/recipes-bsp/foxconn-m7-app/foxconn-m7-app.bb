SUMMARY = "FOXCONN M7-core application"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""
inherit deploy

#FOXCONN_BINARIES_DIR ?= "."
#FOXCONN_M7APP_DIR ?= "${FOXCONN_BINARIES_DIR}"
FOXCONN_M7APP_BIN ?= "m7_app.bin"
S = "${WORKDIR}"

# tell yocto not to strip our binary
INHIBIT_PACKAGE_STRIP = "1"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_unpack[noexec] = "1"
do_fetch() {
        scp autocore@release.autocore.ai:/home/autocore/workspace/Release/Projects/ACOS/S32G_MCore/ACOS_HPCM/${M7_VER}/m7_app.bin ${S}
}

do_install() {
	install -d ${D}/boot
	install -m 0644 "${S}/${FOXCONN_M7APP_BIN}" ${D}/boot
}

do_deploy() {
	install -d ${DEPLOYDIR}
	install -m 0644 ${D}/boot/${FOXCONN_M7APP_BIN} ${DEPLOYDIR}/${FOXCONN_M7APP_BIN}
}

addtask do_deploy after do_install
FILES:${PN} += "/boot"
FILES:${PN} += "/boot/${FOXCONN_M7APP_BIN}"

