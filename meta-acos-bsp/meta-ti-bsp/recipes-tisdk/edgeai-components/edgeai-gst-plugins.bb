SUMMARY = "EdgeAI GST plugins"
DESCRIPTION = "EdgeAI GST plugins implements custom elements to offload compute to HW accelerators and DSPs on TI devices"
HOMEPAGE = "https://github.com/TexasInstruments/edgeai-gst-plugins"

LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1f7721ee7d288457c5a70d0c8ff44b87"

PV = "${SRCPV}"
BRANCH = "main"
SRC_URI = "git://github.com/TexasInstruments/edgeai-gst-plugins.git;branch=${BRANCH};protocol=https"
SRCREV = "a8cd06189cf5aa7884c6cc54a120e69773bb608f"

PLAT_SOC = ""
PLAT_SOC:j721e = "j721e"
PLAT_SOC:j721s2 = "j721s2"
PLAT_SOC:j784s4 = "j784s4"
PLAT_SOC:j722s = "j722s"
PLAT_SOC:am62axx = "am62a"
PLAT_SOC:am62xx = "am62x"
PLAT_SOC:am62pxx = "am62p"

S = "${WORKDIR}/git"

DEPENDS = "edgeai-tiovx-modules edgeai-apps-utils gstreamer1.0-plugins-base edgeai-dl-inferer ti-tidl-osrt"
DEPENDS:remove:adas = " edgeai-dl-inferer ti-tidl-osrt"
RDEPENDS:${PN}-source = "bash meson ninja"

# Remove edgeai-tiovx-modules dependency for ARM only devices
DEPENDS:remove:am62xx = "edgeai-tiovx-modules"
DEPENDS:remove:am62pxx = "edgeai-tiovx-modules"
RDEPENDS:${PN}:remove:am62xx = "edgeai-tiovx-modules"
RDEPENDS:${PN}:remove:am62pxx = "edgeai-tiovx-modules"
RDEPENDS:${PN}-source:remove:am62xx = "edgeai-tiovx-modules-dev"
RDEPENDS:${PN}-source:remove:am62pxx = "edgeai-tiovx-modules-dev"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm|am62xx|am62pxx"
CFLAGS += "-Wno-error=unused-result"
export SOC = "${PLAT_SOC}"

PACKAGES += "${PN}-source"
FILES:${PN}-source += "/opt/"
FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"

EXTRA_OEMESON = "--prefix=/usr -Dpkg_config_path=${S}/pkgconfig"
EXTRA_OEMESON:append:adas = " -Ddl-plugins=disabled"

inherit meson pkgconfig

do_install:append() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-gst-plugins
    cp ${CP_ARGS} ${S}/* ${D}/opt/edgeai-gst-plugins
}

INSANE_SKIP:${PN}-source += "dev-deps"
INSANE_SKIP:${PN} += "rpaths"

PR:append = "_edgeai_0"
