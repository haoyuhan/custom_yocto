# Set extra preferences based on the product branding (AM-SDK, MC-SDK, etc)
# Examples could be different PREFERRED_PROVIDER or PREFERRED_VERSION
# As well as some other configurable aspects or variables
# Please avoid creating multiple versions of the same brand, if possible
# The policy is for the "Core" brand to be the default,
# hence this file is mostly empty and used as a template

# Lower opencl-monitor priority for now
ALTERNATIVE_PRIORITY:pn-opencl-monitor = "1"
ALTERNATIVE_PRIORITY:pn-opencl-monitor-ipu = "1"

# Raise priority for sample IPC FW images
ALTERNATIVE_PRIORITY:pn-ti-ipc-rtos = "15"

# Remove mmip - DRM allocator, KMS sink, Wayland sink need porting to GST 1.18
MACHINE_FEATURES:remove = "mmip"

# GC320 support requires out-of-tree drivers not available in until LTS prep is
# complete. Disable for now.
MACHINE_FEATURES:remove = "gc320"

# Extra boot files for WIC images
do_image_wic[depends] += "tisdk-uenv:do_deploy"
IMAGE_BOOT_FILES += "uEnv.txt"

