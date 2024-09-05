# look for files in this layer first
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += " \
       file://hostname.in \
       file://fstab-gpt.in \
       file://fstab-mbr.in \
"
do_compile:append() {
   sed -e "s#@MACHINE@#${MACHINE}#g" \
    hostname.in > hostname
    if ${@bb.utils.contains('DISTRO_FEATURES', 'acos-gpt', 'true', 'false', d)}; then
    	sed -e "s#@on-disk@#${REPART_DEV}#g" \
    	fstab-gpt.in > fstab
    else
	sed -e "s#@on-disk@#${REPART_DEV}#g" \
        fstab-mbr.in > fstab
    fi
}


