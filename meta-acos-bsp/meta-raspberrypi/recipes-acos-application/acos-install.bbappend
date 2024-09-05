SRC_URI = "
        file://VersionList.txt \
	"
ACOS_INSTALL_DIR = "${D}/${exec_prefix}/acos"
do_install:append(){
    cat >VersionList.txt <<EOF
ACOS_VER = "${ACOS_VER}"
ACOS_APP_VER = "${ACOS_APP_VER}"
MANIFEST_NAME="${MANIFEST_MAIN}"

EOF
    install -m 0644 ${WORKDIR}/VersionList.txt ${ACOS_INSTALL_DIR}

}

