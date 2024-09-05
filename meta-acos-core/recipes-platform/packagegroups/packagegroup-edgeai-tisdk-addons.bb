DESCRIPTION = "Task to install all packages needed on target, for the edgeai/adas SDK"
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    cmake \
    boost \
    ninja \
    meson \
    opencv \
    opencv-dev \
    python3-pyyaml \
    python3-pycparser \
    python3-cffi \
    python3-numpy \
    python3-pybind11 \
    python3-wheel \
    python3-opencv \
    python3-pip \
    python3-pillow \
"

