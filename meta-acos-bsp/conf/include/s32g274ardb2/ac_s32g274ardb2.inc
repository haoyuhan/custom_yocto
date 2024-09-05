IMAGE_BOOT_FILES += "Image"
IMAGE_BOOT_FILES += "s32g274a-rdb2.dtb"
IMAGE_BOOT_FILES += "boot-loader"
IMAGE_BOOT_FILES += "m7_app.bin"
IMAGE_BOOT_FILES += "fip.s32-sdcard"
#IMAGE_BOOT_FILES += "s32g_pfe_class.fw"
DISTRO_FEATURES:append:s32 = " optee hse"
IMAGE_INSTALL:append:s32 = " foxconn-m7-app foxconn-bootloader sja1110 pfe-ko"
# Support for STR (Suspend to RAM) -- rtcwake
#IMAGE_INSTALL:append:s32g = " util-linux-rtcwake "

# Export QSPI FLash script
#IMAGE_INSTALL:append:s32 = " linux-qspi-tool "
# Populate PFE and PFE FW

# Populate PFE slave driver
# Populate PFE and PFE fw
require ${@bb.utils.contains('DISTRO_FEATURES', 'pfe', 'recipes-fsl/images/fsl-image-pfe.inc', '', d)}

#IMAGE_INSTALL:append:s32g = "${@bb.utils.contains('DISTRO_FEATURES', 'pfe-slave', ' pfe-slave', '', d)}"

# Add LLCE CAN if needed
#IMAGE_INSTALL:append:s32g = "${@bb.utils.contains('DISTRO_FEATURES', 'llce-can', ' linux-firmware-llce-can', '', d)}"
# Add OP-TEE user-space components
#IMAGE_INSTALL:append:s32cc = "${@bb.utils.contains('DISTRO_FEATURES', 'optee', ' optee-client optee-examples optee-test ', '', d)}"
# Add PKCS11-HSE Library & Examples
#IMAGE_INSTALL:append:s32cc = "${@bb.utils.contains('DISTRO_FEATURES', 'hse', ' pkcs11-hse', '', d)}"

IMAGE_CLASSES += "image_types_fsl_sdcard"
WKS_FILES = "sdimage-bootpart-mbr.wks.in"
WKS_FILES:acos-gpt = "sdimage-bootpart-gpt.wks.in"
BBMASK:acos += "u-boot-qoriq linux-qoriq"
BBMASK:acos += "meta-freescale/recipes-kernel/lttng/lttng-modules_%.bbappend"
BBMASK:acos += "meta-alb/recipes-connectivity/openssl/openssl_3.0.5.bbappend"
BBMASK:acos += "meta-alb/recipes-fsl/foxconn-bootloader/foxconn-bootloader.bb"
BBMASK:acos += "meta-alb/recipes-kernel/pfe/pfe_1.0.0.bb"
BBMASK:acos += "meta-alb/recipes-fsl/foxconn-m7-app/foxconn-m7-app.bb"
BBMASK:acos += "meta-alb/recipes-extended/xen/xen-tools_git.bbappend"
BBMASK:acos += "meta-alb/recipes-extended/xen/xen_git.bbappend"
#BBMASK:acos += "meta-alb/recipes-bsp/hse/hse-firmware.bb"
# for acos version define
ACOS_HOME = "/opt/autocore"
ACOS_DEPEND_HOME = "/opt/autocore-deps"
ACOS_VER = "AutoCore.OS-V0.24.0-rc1"
ACOS_VER:acos-eea = "AutoCore.OS-V0.24.0-rc1"
AC-SOVDApp:acos-eea = "ACEEA-SOVDApp-R0.3.1"
AC-APP:acos-eea = "ACEEA-App-R0.5.4"
AC-APP-VSS:acos-eea ="ACEEA-App-VSS-R0.0.2"
AC-PMS:acos-eea=""
AC-SysMonitor:acos-eea=""
# for acos fota
ACSYS_S32G_FOTA_VER = "ACSYS_S32G_FOTA_BSP36_V0.22.3"
FOTA_FULL_PAK_NAME = "ACSYS_S32G_FOTAFULL_BSP36_V0.22.3"
FOTA_DIFF_PAK_NAME = "ACSYS_S32G_FOTADIFF_BSP36_V0.22.3"
LAST_FOTA_PRODUCT_VER = "ACSYS_S32G_FOTA_BSP36_V0.22.1"
# for acos branch
MANIFEST_NAME= "acos-custom.xml"
# for s32 nic 
ACOS_NIC_INTERFACE = "pfe0"

# for nxp firmware

M7_VER = "ACOS-HPCM-R1.0.1"
M7_VER:acos-eea = "ACOS-HPCM-R1.0.1"
SWITCH_VER = "EMB/SJA1110/XCU/SJA1110-EEA-R0.2.3/sja1110_eea_hpc_dev.bin"

# wic don't update fstab 
WIC_CREATE_EXTRA_ARGS += " --no-fstab-update"
REPART_DEV="mmcblk0"
REPART_NO="6"
REPART_NO:acos-gpt="5"

IMAGE_ROOTFS_ALIGNMENT="10240"
IMAGE_ROOTFS_ALIGNMENT:acos-gpt="4096"
IMAGE_ALIGNMENT="1024"
IMAGE_BOOT_SIZE="32"
IMAGE_ROOTFS_SIZE="512"
IMAGE_ACOS_SIZE="1536"
IMAGE_CUSTOM_SIZE="100"
