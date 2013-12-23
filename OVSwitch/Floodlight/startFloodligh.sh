#!/bin/sh
echo "Starting Floodlight Controller"
ip=`ifconfig eth1|grep 'inet addr:'|cut -d: -f2| awk '{print $1}'`
echo "At IP: $ip"

nohup java -jar /home/flood/Documents/floodlight/target/floodlight.jar>floodlight.out>Error.err</dev/null &

i=0
res=0
URL="http://$ip:8080/ui/index.html"
floodURL=""
workResp=200
while [ $i -lt 10 ]
do
        res=`curl --write-out "%{http_code}\n" --silent --output /dev/null "$URL"`
        echo $res       
        if [ $res -eq $workResp ]
        then
                echo "Controller started"
                floodURL="http://$ip:8080/ui/index.html"        
                echo "$floodURL"
                break
        fi
        sleep 2s
        i=`expr $i + 1`
done
if [ $i = 10 ]
then
        echo "Controller start error check: /home/flood/Documents/floodlight.out"
fi



