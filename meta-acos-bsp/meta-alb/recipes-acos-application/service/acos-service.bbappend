FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://update_firmware.service.in \
            file://update_firmware.sh.in \
"
do_compile:append() {
    sed -e "s#@ACOS_HOME@#${ACOS_HOME}#g" \
    update_firmware.service.in > update_firmware.service
    sed -e "s#@prefix@#${prefix}#g;s#@base_libdir@#${base_libdir}#g; s#@base_bindir@#${base_bindir}#g; \ 
          s#@base_sbindir@#${base_sbindir}#g;" \
    update_firmware.sh.in > update_firmware.sh
}

do_install:append() {
#        install -D -m 0644 ${WORKDIR}/update_firmware.service ${D}${systemd_system_unitdir}/update_firmware.service
        install -d "${ACOS_INSTALL_DIR}/bin"
        install -m 0755 ${WORKDIR}/update_firmware.sh ${ACOS_INSTALL_DIR}/bin
}
#SYSTEMD_SERVICE:${PN} += "update_firmware.service"
#FILES:${PN} += "${systemd_system_unitidr}/update_firmware.service"
FILES:${PN} += "${ACOS_HOME}/bin/update_firmware.sh"
