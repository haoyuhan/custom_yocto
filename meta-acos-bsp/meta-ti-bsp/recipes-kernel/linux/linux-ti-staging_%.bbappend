FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/${MACHINE}:"

PR:append = "_edgeai_0"

require rtos-mem-map.inc
