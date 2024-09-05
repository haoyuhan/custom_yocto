FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti-staging/${MACHINE}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-ti-staging:"

PR:append = "_edgeai_0"

require rtos-mem-map.inc
