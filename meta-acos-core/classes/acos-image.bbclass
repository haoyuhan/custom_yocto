require conf/include/${MACHINE}/make_${MACHINE}_wic.inc


#
# Modify systemd default target
#
set_init_to_systemd_link () {
                ln -sf ${exec_prefix}/lib/systemd/systemd ${IMAGE_ROOTFS}${exec_prefix}/sbin/init
}
ROOTFS_POSTPROCESS_COMMAND += '${@bb.utils.contains("DISTRO_FEATURES", "systemd", "set_init_to_systemd_link; ", "", d)}'

# Enable local auto-login of the root user (local = serial port and
# virtual console by default, can be configured).
OSTRO_LOCAL_GETTY ?= " \
    ${IMAGE_ROOTFS}${systemd_system_unitdir}/serial-getty@.service \
    ${IMAGE_ROOTFS}${systemd_system_unitdir}/getty@.service \
"
local_autologin () {
    sed -i -e 's/^\(ExecStart *=.*getty \)/\1--autologin root /' ${OSTRO_LOCAL_GETTY}
}
ROOTFS_POSTPROCESS_COMMAND += "${@bb.utils.contains('IMAGE_FEATURES', 'autologin', 'local_autologin;', '', d)}"
IMAGE_FEATURES[validitems] += "autologin"


set_nic_for_networkd_online () {
                sed -i '/^ExecStart=/c\ExecStart=/usr/lib/systemd/systemd-networkd-wait-online --interface=${ACOS_NIC_INTERFACE}' \
        ${IMAGE_ROOTFS}${exec_prefix}/lib/systemd/system/systemd-networkd-wait-online.service
}
ROOTFS_POSTPROCESS_COMMAND += '${@bb.utils.contains("DISTRO_FEATURES", "systemd", "set_nic_for_networkd_online; ", "", d)}'

