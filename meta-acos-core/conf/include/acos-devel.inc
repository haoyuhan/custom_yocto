IMAGE_FEATURES =+ "debug-tweaks tools-debug"

IMAGE_INSTALL:append = " \
    packagegroup-acos-core-devel \
"
# disable install of debug files in SDK
# initial value: SDKIMAGE_FEATURES="dev-pkgs dbg-pkgs staticdev-pkgs"
SDKIMAGE_FEATURES="dev-pkgs"

# allows insertion of code or items specific to developement
OVERRIDES:append = ":acos-devel"
DISTRO_FEATURES:append = " acos-devel"
