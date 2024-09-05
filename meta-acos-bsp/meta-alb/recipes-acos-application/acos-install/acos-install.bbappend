do_install:append:acos-eea() {
	if [ -n "${AC-SOVDApp}" ]; then	
   		wget -nv -P ${WORKDIR} http://release.autocore.ai/Projects/ACEEA/ACEEA_APP/${AC-SOVDApp}/${AC-SOVDApp}-linux-aarch64.tar.gz
		tar --no-same-owner -zxf ${WORKDIR}/${AC-SOVDApp}-linux-aarch64.tar.gz -C ${D}/
	fi

	if [ -n "${AC-APP}" ]; then
   		wget -nv -P ${WORKDIR} http://release.autocore.ai/Projects/ACEEA/ACEEA_APP/${AC-APP}/${AC-APP}-linux-aarch64.tar.gz
		tar --no-same-owner -zxf ${WORKDIR}/${AC-APP}-linux-aarch64.tar.gz -C ${D}/
	fi

	if [ -n "${AC-APP-VSS}" ]; then
                wget -nv -P ${WORKDIR} http://release.autocore.ai/Projects/ACEEA/ACEEA_APP/${AC-APP-VSS}/${AC-APP-VSS}-linux-aarch64.tar.gz
		install -d ${ACOS_INSTALL_DIR}/AC_APP_VSS
                tar --no-same-owner -zxf ${WORKDIR}/${AC-APP}-linux-aarch64.tar.gz --strip-components 3 -C ${D}/${ACOS_HOME}/AC_APP_VSS

        fi


	if [ -n "${AC-PMS}" ]; then
   		wget -nv -P ${WORKDIR} http://release.autocore.ai/PlatformService/pm/${AC-PMS}/${AC-PMS}-linux-aarch64.tar.gz
   		tar --no-same-owner -zxf ${WORKDIR}/${AC-PMS}-linux-aarch64.tar.gz -C ${ACOS_INSTALL_DIR}/apps/
	fi

	if [ -n "${AC-SysMonitor}" ]; then
		wget -nv -P ${WORKDIR} http://release.autocore.ai/Projects/AutoCore.SystemMonitor/${AC-SysMonitor}/${AC-SysMonitor}-linux-aarch64.tar.gz
		tar  --no-same-owner -zxf ${WORKDIR}/${AC-SysMonitor}-linux-aarch64.tar.gz -C ${ACOS_INSTALL_DIR}/
	fi
}

