ARAGO_BRAND = "edgeai"
MACHINE = "j721e-evm"
PACKAGE_CLASSES = "package_ipk"
require conf/distro/include/branding-${ARAGO_BRAND}.inc
PACKAGECONFIG:remove = "glesv2"
PACKAGECONFIG:append:pn-libcamera = " gst"
WKS_FILES = "sdimage-efipart.wks.in"
DISTRO_FEATURES += "pam wayland opengl vulkan opencv openmp"
LICENSE_FLAGS_ACCEPTED += "commercial"
PACKAGECONFIG_GL:pn-qtbase = "gles2 linuxfb"
PACKAGECONFIG:pn-glmark2 = "drm-gles2 wayland-gles2"
PACKAGECONFIG_GL:pn-libsdl2 = "gles2"
IMAGE_BOOT_FILES += "${SPL_BINARYNAME} u-boot.${UBOOT_SUFFIX} tiboot3.bin uEnv.txt"
# it's test for tda4 temp set to no
SECURITY_STRINGFORMAT = "" 
REPART_DEV = "mmcblk1"
REPART_NO="5"

ACOS_NIC_INTERFACE = "eth0"
ACOS_VER = "AutoCore.OS-V0.22.3"
