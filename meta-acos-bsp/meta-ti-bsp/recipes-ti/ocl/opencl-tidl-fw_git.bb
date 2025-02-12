SUMMARY = "OpenCL TIDL firmware for AM57xx"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE.ti;md5=082a028431c455252c1e1d3d1021d382"

PV = "01.02.00.01"
PR = "r0"

require meta-ti-bsp/recipes-ti/includes/arago-paths.inc
COMPATIBLE_MACHINE = "dra7xx"

PACKAGE_ARCH = "${MACHINE_ARCH}"

GIT_URI      = "git://git.ti.com/git/opencl/opencl-firmware.git"
GIT_PROTOCOL = "https"
BRANCH       = "master"
SRCREV       = "9e3d0b34f604203f275fba4807481a8a763a4f63"

SRC_URI      = "${GIT_URI};protocol=${GIT_PROTOCOL};branch=${BRANCH}"

S = "${WORKDIR}/git"

TARGET = "eve_firmware.bin ocl_tidl_dsp.lib"

do_install() {
    install -d ${D}${OCL_TIDL_FW_INSTALL_DIR_RECIPE}
    for f in ${TARGET}; do
	install -m 0644 ${S}/$f ${D}${OCL_TIDL_FW_INSTALL_DIR_RECIPE}/$f
    done
}

FILES:${PN} += "${OCL_TIDL_FW_INSTALL_DIR_RECIPE}"

INSANE_SKIP:${PN} = "arch"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
