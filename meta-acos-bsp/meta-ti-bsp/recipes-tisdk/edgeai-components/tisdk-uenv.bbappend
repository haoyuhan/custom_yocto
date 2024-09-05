FILESEXTRAPATHS:prepend:edgeai := "${THISDIR}/${PN}/${MACHINE}/edgeai:${THISDIR}/${PN}/${MACHINE}:${THISDIR}/${PN}:"
FILESEXTRAPATHS:prepend:adas := "${THISDIR}/${PN}/${MACHINE}/adas:${THISDIR}/${PN}/${MACHINE}:${THISDIR}/${PN}:"

PR:append = "_edgeai_0"
