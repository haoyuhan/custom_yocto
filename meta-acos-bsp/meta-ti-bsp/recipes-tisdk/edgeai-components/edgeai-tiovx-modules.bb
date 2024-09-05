SUMMARY = "EdgeAI TIOVX modules"
DESCRIPTION = "EdgeAI TIOVX modules implements simple APIs to create and use OpenVX nodes"
HOMEPAGE = "https://git.ti.com/cgit/edgeai/edgeai-tiovx-modules"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f7721ee7d288457c5a70d0c8ff44b87"

PV = "${SRCPV}"
BRANCH = "main"
SRC_URI = "git://git.ti.com/git/edgeai/edgeai-tiovx-modules.git;branch=${BRANCH};protocol=https"
SRCREV = "1d2c6a739d7367df1db4360820f6d1da6165bd42"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:j722s = "j722s"
PLAT_SOC:am62axx = "am62a"

S = "${WORKDIR}/git"

DEPENDS = "ti-vision-apps edgeai-tiovx-kernels"
RDEPENDS:${PN}-source = "cmake"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm"

export SOC = "${PLAT_SOC}"

EXTRA_OECMAKE = "-DTARGET_FS=${WORKDIR}/recipe-sysroot -DINSTALL_SRC=on -DCMAKE_SKIP_RPATH=TRUE -DCMAKE_OUTPUT_DIR=${WORKDIR}/out"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"

inherit cmake

PR:append = "_edgeai_0"
