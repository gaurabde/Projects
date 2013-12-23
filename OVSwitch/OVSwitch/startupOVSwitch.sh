#!/bin/sh
echo "Starting OVSwitch server"
#BASEDIR="`dirname $0`"
BASEDIR="$(dirname $0)"
pushd $BASEDIR > /dev/null 2>&1

#Loading OVSwitch kernel
echo "Loading OVSwitch Kernel from base-directory: $BASEDIR"
insmod $BASEDIR/openvswitch-1.10.0/datapath/linux/openvswitch.ko
echo "Kernel loaded "

echo "Staring OVSwitch server"
ovsdb-server -v --remote=punix:/usr/local/var/run/openvswitch/db.sock \
                     --remote=db:Open_vSwitch,manager_options \
                     --private-key=db:SSL,private_key \
                     --certificate=db:SSL,certificate \
                     --pidfile --detach --log-file
echo "OVSwitch server started"
echo "ovs-vsctl starting"
ovs-vsctl --no-wait init
echo "ovs-vsctl end"
echo "Vswitch starting"
ovs-vswitchd --pidfile --detach --log-file=/home/ovswitch/Documents/ovs-vswitchd.log 

echo "VSwitch end"

nohup kvm -m 256 -nographic -net nic,macaddr=00:00:00:00:cc:10 -net tap,script=/etc/ovs-ifup,downscript=/etc/ovs-ifdown -hda  /home/ovswitch/Documents/linux.vdi &



