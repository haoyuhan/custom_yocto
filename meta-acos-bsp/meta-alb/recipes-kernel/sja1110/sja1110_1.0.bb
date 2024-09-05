# Copyright 2020 NXP
SUMMARY = "sja1110_uc firmware update"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

inherit deploy

SJA_LIBDIR = "${base_libdir}/firmware"
SJA1110_UC_FW = "sja1110_uc.bin"

SRC_URI += " \
           file://${SJA1110_UC_FW} \
           "

# tell yocto not to strip our binary
INHIBIT_PACKAGE_STRIP = "1"
S = "${WORKDIR}"
do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_unpack[noexec] = "1"
do_fetch() {
        scp autocore@release.autocore.ai:/home/autocore/workspace/Release/${SWITCH_VER}  ${S}/${SJA1110_UC_FW}
}

do_install() {
        install -d ${D}/${SJA_LIBDIR}
        install -m 0644 "${S}/${SJA1110_UC_FW}" ${D}/${SJA_LIBDIR}
}

do_deploy() {
        install -d ${DEPLOYDIR}
        install -m 0644 ${D}/${SJA_LIBDIR}/${SJA1110_UC_FW} ${DEPLOYDIR}/${SJA1110_UC_FW}
}

addtask do_deploy after do_install

FILES:${PN} += "${SJA_LIBDIR}/${SJA1110_UC_FW}"

