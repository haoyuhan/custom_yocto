addhandler acos-core_bbappend_distrocheck
acos-core_bbappend_distrocheck[eventmask] = "bb.event.SanityCheck"
python acos-core_bbappend_distrocheck() {
    skip_check = e.data.getVar('SKIP_META_ACOS_CORE_SANITY_CHECK') == "1"
    if 'acos-core' not in e.data.getVar('ACOS_FEATURES').split() and not skip_check:
        bb.warn("You have included the meta-acos-core layer, but \
'acos-core' has not been enabled in your ACOS_FEATURES. Some bbappend files \
may not take effect. See the meta-acos-core README for details on enabling \
meta-acos-core support.")
}
