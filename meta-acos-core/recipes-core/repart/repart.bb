SUMMARY = "resize partition for data"
DESCRIPTION = "Recipe for resizin the partition and filesystem"
LICENSE = "CLOSED"

REPART_DEV ??= ""
REPART_NO ??= ""
REPART_MOUNTPOINT ??= ""

ALLOW_EMPTY:${PN} = "1"

SRC_URI = " \
           file://repart.service.in \
           file://repart.sh.in \
           file://repart-gpt.sh.in \
	   "
DEPENDS = "bc-native"
RDEPENDS:${PN} = "bash parted"
inherit systemd features_check
REQUIRED_DISTRO_FEATURES = "systemd"

S = "${WORKDIR}"
do_compile() {
    if [ -z "${REPART_DEV}" ]; then
        bbfatal "REPART_DEV should be set."
    fi
    if [ -z "${REPART_NO}" ]; then
        bbfatal "REPART_NO should be set."
    fi
    
    if [ ${@bb.utils.contains('DISTRO_FEATURES', 'acos-gpt', 'true', 'false', d)} = "false" ]; then
    	sed -e "s#@repart-dev@#/dev/${REPART_DEV}#g; s#@repart-no@#${REPART_NO}#g; s#@repart-ext-no@#${REPART_EXT_NO}#g" \
    	repart.sh.in >  repart.sh
    else
        IMAGE_CUSTOM_START=$(echo " ${IMAGE_ROOTFS_ALIGNMENT}/1024 + ${IMAGE_BOOT_SIZE} + ${IMAGE_ROOTFS_SIZE} + ${IMAGE_ACOS_SIZE} + ${IMAGE_ALIGNMENT}/1024" | bc)
	IMAGE_CUSTOM_END=$(echo " ${IMAGE_CUSTOM_START} + ${IMAGE_CUSTOM_SIZE}" | bc)
	IMAGE_DATA_START=$(echo " ${IMAGE_CUSTOM_END} + ${IMAGE_ALIGNMENT}/1024" | bc)
	IMAGE_DATA_END=$(echo " ${IMAGE_DATA_START} + 10" | bc)
    	sed -e "s#@repart-dev@#/dev/${REPART_DEV}#g; s#@repart-no@#${REPART_NO}#g; s#@repart-ext-no@#${REPART_EXT_NO}#g; \
     		s#@IMAGE_CUSTOM_START@#${IMAGE_CUSTOM_START}#g;s#@IMAGE_CUSTOM_END@#${IMAGE_CUSTOM_END}#g; \
		s#@IMAGE_DATA_START@#${IMAGE_DATA_START}#g;s#@IMAGE_DATA_END@#${IMAGE_DATA_END}#g" \
        repart-gpt.sh.in >  repart.sh

    fi
    sed -e "s#@mount-service@#${REPART_MOUNT_SERVICE}#g" \
    repart.service.in > repart.service
}

do_install() {
    if [ -n "${REPART_MOUNTPOINT}" ]; then
        install -d ${D}${REPART_MOUNTPOINT}
    fi

    if [ -n "${REPART_MOUNTCUSTOM}" ]; then
        install -d ${D}${REPART_MOUNTCUSTOM}
    fi
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/repart.service ${D}${systemd_system_unitdir}
    
    install -d ${D}/${sbindir}
    install -m 0755 ${WORKDIR}/*.sh ${D}/${sbindir}

    echo "# acos repart data .." > ${D}/usr/sbin/.autorepart
}

SYSTEMD_SERVICE:${PN} = "repart.service"

FILES:${PN} = "\
  ${systemd_system_unitdir} \
  ${REPART_MOUNTPOINT} \
  ${REPART_MOUNTCUSTOM} \
  ${sbindir} \  
"
FILES:${PN} += "/usr/sbin/.autorepart"
