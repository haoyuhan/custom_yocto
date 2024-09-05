#!/bin/bash
set +v
set +x
target_device="/dev/sdb"
#define echo print color.
RED_COLOR='\E[1;31m'
PINK_COLOR='\E[1;35m'
YELOW_COLOR='\E[1;33m'
BLUE_COLOR='\E[1;34m'
GREEN_COLOR='\E[1;32m'
END_COLOR='\E[0m'
PLAIN='\033[0m'
echo -e "\n welcome to download sh \n"
echo "start download time ..."
date +"%Y%m%d %H:%M:%S"
echo -e "\n\n"
# Check user must root.
check_root() {
    if [ $(id -u) != "0" ]; then
        echo -e "${RED_COLOR}Error: This script must be run as root!${END_COLOR}"
        exit 1
    fi
}
# Check set linux host user name.
check_user_name() {
    user_name="$USER"
    cat /etc/passwd|grep $user_name
    if [ $? -eq 0 ];then
        echo -e "${BLUE_COLOR}Check the set user name OK.${END_COLOR}"
	sudo sh -c "echo \"$user_name ALL=(ALL) NOPASSWD:ALL\" >> /etc/sudoers"
        echo -e "${RED_COLOR}Add $user_name to sudoers !${END_COLOR}"
    fi
}

install_tool() {
need_tool=(sshpass bmap-tools)
# verify tool insatll
for tool in "${need_tool[@]}"; do
        apt-get install -y $tool
done
}

init_download_env() {
  file_name=".init_env_done"
  need_tool=(sshpass bmap-tools)
  if [ ! -f $file_name ] ;then
    check_user_name
    install_tool
    touch .init_env_done
  else
    echo "env has init"
  fi
}
validate_device() {
  local prompt="Please select device (sdb, sdc): default sdb "
  read -t 5 -p "$prompt" input

  if [ -z "$input" ]; then
    echo "Timeout: Setting device to default (/dev/sdb)."
    target_device="/dev/sdb"
  else
    target_device="/dev/$input" 
  fi
}

init_download_env
    

# Select device with timeout and validation (optional)
validate_device
target_image_name="acos-image-minimal-s32g274ardb2.wic.bz2"
# Display device and image name
echo "Target Image Name: $target_image_name"
echo "Target Device: $target_device"

# Get disk size (assuming all partitions have the same size) (Optional)
#part_num=$(ls $target_device* | wc -l)
# Unmount partitions (up to disk size) (Optional)
#sudo umount $target_device
#for ((i = 1; i <part_num; i++)); do
#   sudo umount "$target_device$i" &>/dev/null  # Suppress potential errors
#done

sleep 3
sudo umount ${target_device}*
sudo bmaptool copy $target_image_name $target_device

echo -e "\n"
echo "end download time ..."
date +"%Y%m%d %H:%M:%S"


