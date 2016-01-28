#!/bin/bash

/usr/bin/nmcli c add autoconnect yes save yes con-name iotdemo ifname wlp0s20u2 type wifi ssid iotdemo  mode ap ip4 192.168.42.1/24
/usr/bin/nmcli c mod iotdemo  802-11-wireless-security.key-mgmt wpa-psk 802-11-wireless-security.psk change12_me
/usr/bin/nmcli c modify iotdemo 802-11-wireless-security.pairwise ccmp
/usr/bin/nmcli c modify iotdemo 802-11-wireless-security.proto rsn
/usr/bin/nmcli c up iotdemo
