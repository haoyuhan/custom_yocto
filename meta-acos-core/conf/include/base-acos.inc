# Disable kernel module tarballs by default on the assumption that
# desired modules will already be present in images.
# Comment this out to receive the separate modules tarball again.
MODULE_TARBALL_DEPLOY ?= "0"

# Comment out below if want to use QtWebkit
PACKAGECONFIG:remove:pn-qtquick1 = "webkit"


ASSUME_PROVIDED:remove = "tar-native"

# Use static IDs to enable full-filesystem updates
USERADDEXTENSION = "useradd-staticids"
USERADD_ERROR_DYNAMIC = "error"
USERADD_GID_TABLES = "files/group"
USERADD_UID_TABLES = "files/passwd"
PREFERRED_VERSION_openssl = "3.0.13"
ACOS_FEATURES += "acos-core"
IMAGE_CLASSES += "acos-image"
IMAGE_FSTYPES += "wic wic.bmap"
IMAGE_FSTYPES:remove = "sdcard ext4  cpio.gz.u-boot"
WKS_FILES ?= "sdimage-bootpart.wks.in"
PACKAGE_CLASSES:append = " package_ipk"
PACKAGE_CLASSES:remove = "package_rpm"

## systemd 

DISTRO_FEATURES:append = " systemd usrmerge"
DISTRO_FEATURES_BACKFILL_CONSIDERED += "sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"
VIRTUAL-RUNTIME_initscripts = "systemd-compat-units"

## log persitent

VOLATILE_LOG_DIR = "no"

ACOS_HOME ?= "/opt/autocore"
ACOS_DEPEND_HOME ?= "/opt/autocore-deps"

## deploy dir

#DEPLOY_DIR_IMAGE = "${DEPLOY_DIR}/images/${MACHINE}_${PRODUCT}"
