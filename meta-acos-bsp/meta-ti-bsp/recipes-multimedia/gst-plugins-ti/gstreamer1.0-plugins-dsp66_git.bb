DESCRIPTION = "GStreamer elements to use the TI DSP C66 in multimedia applications"
HOMEPAGE = "https://git.ti.com/processor-sdk/gst-plugin-dsp66"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=2827f94fc0a1adeff4d9702e97ce2979"

SRC_URI = "git://git.ti.com/git/processor-sdk/gst-plugin-dsp66.git;protocol=https;branch=master \
	file://0001-Makefile-correct-use-of-CPP-CXX-and-other-standard-v.patch \
	file://0001-configure.ac-stop-using-export-symbols-regex.patch \
"
SRCREV = "0abedafadbed693592804f23482a9447d81b2dbf"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "dra7xx"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base opencl ti-cgt6x-native clocl-native imglib-c66x vlib-c66x gettext-native"

inherit autotools-brokensep pkgconfig gettext
inherit features_check

REQUIRED_MACHINE_FEATURES = "dsp"
REQUIRED_DISTRO_FEATURES = "opencl"

PR = "r1"

RDEPENDS:${PN} += "opencl-runtime"

EXTRA_OEMAKE = " TARGET_ROOTDIR=${STAGING_DIR_HOST} \
                 TI_OCL_CGT_INSTALL=${STAGING_DIR_NATIVE}/usr/share/ti/cgt-c6x \
"

do_configure() {
	cd ${S}
	chmod +x autogen.sh
	./autogen.sh --host=arm-linux --with-libtool-sysroot=${STAGING_DIR_TARGET} --prefix=/usr
}

EXTRA_OECONF += "--enable-maintainer-mode"
EXTRA_OEMAKE += "'ERROR_CFLAGS=-Wno-deprecated-declarations'"

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-dbg += "${libdir}/gstreamer-1.0/.debug"
FILES:${PN}-dev += "${libdir}/gstreamer-1.0/*.la"

FILES:${PN} += "${libdir}/*.so"
FILES:${PN}-dbg += "${libdir}/.debug"
FILES:${PN}-dev += "${libdir}/*.la"
FILES_SOLIBSDEV = ""

INSANE_SKIP:${PN} = "ldflags"
