SUMMARY = "Provides a set of tools for development for ACOS DISTRO"
LICENSE = "MIT"
### ltp version
PREFERRED_VERSION_ltp = "202040809"

inherit packagegroup
RDEPENDS:${PN} = "\
        strace \
        ldd \
        less \
        vim \
        lsof \
        gdb \
        screen \
        usbutils \
        rsync \
        pstree \
        procps \
        libxslt-bin \
        pciutils \
        openssh-sftp-server \
        zstd \
        wpa-supplicant \
	packagegroup-core-ssh-openssh vim ldconfig patchelf iperf3 perf procps valgrind gdb wpa-supplicant tcpdump ethtool apt \
	python3-pip python3-paramiko python3-six python3-pytest python3-pytest-html python3-attrs python3-pluggy python3-tomli python3-iniconfig python3-packaging python3-pytest-metadata python3-jinja2 python3-markupsafe \
	util-linux can-utils iproute2 expect perl perl-modules phytool dpkg netcat-openbsd libcap libcap-bin rt-tests ltp \
"

