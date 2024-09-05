#
# Create an image that can by written onto a SD card using dd for use
# with i.MX, S32, or Layerscape SoC families
#
# External variables needed:
#   ${SDCARD_ROOTFS}    - the rootfs image type to incorporate, e.g., ext3
#   ${SDCARD_ROOTFS_EXTRA1_*} - Optional additional partition definition
#   ${SDCARD_RCW_NAME}  - RCW for Layerscape devices
#   ${IMAGE_BOOTLOADER} - bootloader to use {u-boot, barebox}
#
# The disk layout used is:
#
#    0                      -> IMAGE_ROOTFS_ALIGNMENT         - reserved to bootloader (not partitioned)
#    IMAGE_ROOTFS_ALIGNMENT -> BOOT_SPACE                     - kernel and other data
#    BOOT_SPACE             -> SDIMG_SIZE                     - rootfs
#
#                                                     Default Free space = 1.3x
#                                                     Use IMAGE_OVERHEAD_FACTOR to add more space
#                                                     <--------->                                              (optional)
#            4MiB               8MiB           SDIMG_ROOTFS0                    4MiB                          SDIMG_ROOTFSn                   4MiB
# <-----------------------> <----------> <----------------------> <----------------------------->       <----------------------> <----------------------------->
#  ------------------------ ------------ ------------------------ ------------------------------- ..... ------------------------ -------------------------------
# | IMAGE_ROOTFS_ALIGNMENT | BOOT_SPACE | ROOTFS_SIZE            | BASE_IMAGE_ROOTFS_ALIGNMENT  |       | ROOTFS_SIZE           | BASE_IMAGE_ROOTFS_ALIGNMENT  |
#  ------------------------ ------------ ------------------------ ------------------------------- ..... ------------------------ -------------------------------
# ^                        ^            ^                        ^                                      ^                                                      ^
# |                        |            |                        |                                      |                                                      |
# 0                      4096     4MiB + 8MiB       4MiB + 8Mib + SDIMG_ROOTFS            12MiB + (SDIMG_ROOTFS + 4MiB) * n                 12MiB + (SDIMG_ROOTFS + 4MiB) * (n + 1)
#
#                                       |                                                       |       |                                                      |
#                                       |                                                       |       |                                                      |
#                                        <----------------------------------------------------->  .....  <---------------------------------------------------->
#                                                                   |                                                              |
#                                                                ROOTFS0                                                        ROOTFSn
do_make_bootloader_wic[depends] += "pbzip2-native:do_populate_sysroot"
do_make_bootloader_wic[depends] += "bmap-tools-native:do_populate_sysroot"
do_make_bootloader_wic[depends] += "arm-trusted-firmware:do_deploy"
#do_make_bootloader_wic[depends] += "pfe-firmware:do_install"
do_make_bootloader_wic[depends] += "foxconn-bootloader:do_deploy"
do_make_bootloader_wic[depends] += "foxconn-m7-app:do_deploy"
do_make_bootloader_wic[depends] += "sja1110:do_deploy"
do_image_wic[depends] += "arm-trusted-firmware:do_deploy"
UBOOT_NAME_SDCARD ?= "fip.s32-sdcard"
do_make_bootloader_wic() {
    cd ${DEPLOY_DIR_IMAGE}
    if [ -f ${IMAGE_LINK_NAME}.wic ]; then
    	if [ ${@bb.utils.contains('DISTRO_FEATURES', 'acos-gpt', 'true', 'false', d)} = "false" ]; then
		dd if=fip.s32-sdcard of=${IMAGE_LINK_NAME}.wic conv=notrunc bs=256 count=1 seek=0
		dd if=fip.s32-sdcard of=${IMAGE_LINK_NAME}.wic conv=notrunc bs=512 seek=1 skip=1
    	fi
	bmaptool create ${IMAGE_LINK_NAME}.wic -o ${IMAGE_LINK_NAME}.wic.bmap
        pbzip2 -f -k ${IMAGE_LINK_NAME}.wic
    fi

}

addtask make_bootloader_wic after do_image_complete before do_build

