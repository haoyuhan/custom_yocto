SUMMARY = "TI GPIO cpp"
DESCRIPTION = "A Linux based CPP library for TI GPIO RPi header enabled platforms"
HOMEPAGE = "https://github.com/TexasInstruments/ti-gpio-cpp"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.txt;md5=4a6102d7daa29b70c1abe088c13a0cde"

SRC_URI = "git://github.com/TexasInstruments/ti-gpio-cpp.git;protocol=https;branch=master"
SRCREV = "3cce00c71b9f8614d0d3ec493b3969bdb0866f69"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm|am62pxx-evm"

EXTRA_OECMAKE = "-DCMAKE_SKIP_RPATH=TRUE"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"

inherit cmake

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/ti-gpio-cpp
    cp ${CP_ARGS} ${S}/* ${D}/opt/ti-gpio-cpp
    rm -rf ${D}/opt/ti-gpio-cpp/lib
    rm -rf ${D}/usr/cmake
}

PR:append = "_edgeai_2"
