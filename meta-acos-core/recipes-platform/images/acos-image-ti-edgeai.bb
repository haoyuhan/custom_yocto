SUMMARY = "An ACOS small image just capable of allowing a device to boot."
LICENSE = "MIT"
require tisdk-default.inc
inherit acos-core-image
EDGEAI_STACK = " \
        ti-vision-apps-dev \
        ti-edgeai-firmware \
        ti-tidl-dev \
        edgeai-tiovx-kernels-dev \
        edgeai-tiovx-modules-dev \
        edgeai-tiovx-kernels-source \
        edgeai-tiovx-modules-source \
        edgeai-apps-utils-source \
        edgeai-test-data \
        edgeai-tidl-models \
        edgeai-tiovx-apps-dev \
        edgeai-tiovx-apps-source \
"

EDGEAI_STACK:append:edgeai = " \
        ti-tidl-osrt-dev \
        ti-tidl-osrt-staticdev \
        edgeai-init \
        edgeai-gui-app \
        edgeai-gst-plugins-dev \
        edgeai-dl-inferer-staticdev \
        edgeai-gst-apps-source \
        edgeai-gst-plugins-source \
        edgeai-gst-apps \
        edgeai-dl-inferer-source \
        ti-gpio-cpp \
        ti-gpio-py \
        ti-gpio-cpp-source \
        ti-gpio-py-source \
"

EDGEAI_STACK:append:adas = " \
        ti-tidl-osrt-staticdev \
        edgeai-gst-plugins \
"

IMAGE_INSTALL += " \
                  packagegroup-acos-image-minimal \
                  ${CORE_IMAGE_EXTRA_INSTALL} \
                  ${EDGEAI_STACK} \
                  packagegroup-arago-gst-sdk-target \
                  packagegroup-edgeai-tisdk-addons \
"
IMAGE_INSTALL:append:j721e = " pmic-fix"

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"
