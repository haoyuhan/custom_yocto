SUMMARY = "GPIO ZERO Python Library"
HOMEPAGE = "https://github.com/gpiozero/gpiozero"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${S}/LICENSE.rst;md5=f7edfe7aeac02cb6c394726db07eb41c"

SRC_URI = " \
    git://github.com/gpiozero/gpiozero.git;protocol=https;branch=master \
    file://gpiozero.patch \
"
SRCREV = "2b6aa5314830fedf3701113b6713161086defa38"

S = "${WORKDIR}/git"

inherit setuptools3

PR:append = "_edgeai_0"
