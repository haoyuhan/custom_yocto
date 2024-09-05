SUMMARY = "EdgeAI Test Data"
LICENSE = "TI-TFL"
LIC_FILES_CHKSUM = "file://${ACOS_CUSTOM_BSP}/meta-ti-bsp/licenses/TI-TFL;md5=a1b59cb7ba626b9dbbcbf00f3fbc438a"

export http_proxy
export https_proxy
export no_proxy

BRANCH = "main"
SRCREV = "21dce9bd8daa40722a3483adf586f8499f52262c"

SOC = ""
SOC:j721e = "j721e"
SOC:j721s2 = "j721s2"
SOC:j784s4 = "j784s4"
SOC:j722s = "j722s"
SOC:am62axx = "am62a"
SOC:am62xx = "am62x"
SOC:am62pxx = "am62p"

COMPATIBLE_MACHINE = "j721e-evm|j721e-hs-evm|j721s2-evm|j721s2-hs-evm|j784s4-evm|j784s4-hs-evm|j722s-evm|am62axx-evm|am62xx|am62pxx"

do_fetch() {
    mkdir -p ${WORKDIR}/script
    cd ${WORKDIR}/script
    if [ "${SRCREV}" = "${AUTOREV}" ]; then
        VERSION="${BRANCH}"
    else
        VERSION="${SRCREV}"
    fi
    wget https://raw.githubusercontent.com/TexasInstruments/edgeai-gst-apps/${VERSION}/download_test_data.sh
    chmod +x ./download_test_data.sh

    export SOC="${SOC}"
    export EDGEAI_DATA_PATH=${WORKDIR}/edgeai-test-data
    export OOB_DEMO_ASSETS_PATH=${WORKDIR}/oob-demo-assets
    export EDGEAI_SDK_VERSION=09_02_00

    ./download_test_data.sh

    cd $OOB_DEMO_ASSETS_PATH
    for i in *.h264
    do
      ln -sf /opt/oob-demo-assets/$i $EDGEAI_DATA_PATH/videos/$i
    done
}

do_install() {
    CP_ARGS="-Prf --preserve=mode,timestamps --no-preserve=ownership"

    mkdir -p ${D}/opt/edgeai-test-data
    mkdir -p ${D}/opt/oob-demo-assets

    cp ${CP_ARGS} ${WORKDIR}/edgeai-test-data/* ${D}/opt/edgeai-test-data
    cp ${CP_ARGS} ${WORKDIR}/oob-demo-assets/* ${D}/opt/oob-demo-assets
}

FILES:${PN} += "/opt/"
SSTATE_SKIP_CREATION = "1"

PR:append = "_edgeai_0"
