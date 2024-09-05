# Maximum memory allowed to be assigned
GPU_MEM = "256"

# Add CMA to the kernel arguments for SOTA
OSTREE_KERNEL_ARGS:sota:append = " cma=256M"

# use u-boot always
RPI_USE_U_BOOT = "1"

# Use UART for serial console
ENABLE_UART ?= "1"

# For libomxil
#LICENSE_FLAGS_ACCEPTED = "commercial"
LICENSE_FLAGS_ACCEPTED = "synaptics-killswitch"
IMAGE_INSTALL:append = " kernel-modules linux-firmware-rpidistro-bcm43430 linux-firmware-rpidistro-bcm43455 create-combined-dtb "

# Psplash causes crash on first boot on RPi
IMAGE_FEATURES:remove = "splash"

PREFERRED_PROVIDER_virtual/network-config = "network-config-raspberry"
PREFERRED_PROVIDER_virtual/acos-service = "acos-service-raspberry"
WKS_FILES = "sdimage-raspberrypi.wks.in"

ACOS_NIC_INTERFACE = "eth0"
ACOS_VER = "AutoCore.OS-V0.23.0"
