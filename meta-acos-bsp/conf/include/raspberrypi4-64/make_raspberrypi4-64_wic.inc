do_make_bootloader_wic[depends] += "pbzip2-native:do_populate_sysroot"


do_make_bootloader_wic() {
    cd ${DEPLOY_DIR_IMAGE}
    bmaptool create ${IMAGE_LINK_NAME}.wic -o ${IMAGE_LINK_NAME}.wic.bmap
    pbzip2 -f -k ${IMAGE_LINK_NAME}.wic
}

addtask make_bootloader_wic after do_image_complete before do_build

