#!/bin/bash

export deviceType="temperature"
export deviceID="4711"
export initialValue="70"
export count="2"
export waitTime="1"

java -DdeviceType=$deviceType -DdeviceID=$deviceID -DinitialValue=$initialValue -Dcount=$count -DwaitTime=$waitTime -jar Producer/target/iot_producer-jar-with-dependencies.jar
