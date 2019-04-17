#!/bin/bash
java -jar /wiremock/wiremock-standalone-2.22.0.jar --port 8080 --root-dir /wiremock/config/ &     #start mock server
echo 'wiremock init done...' &&
sleep 10s &&
$ANDROID_HOME/emulator/emulator -avd emulator-5554 -skin 1080x1920 -no-window &         #start android emulator
echo 'emulator init done...' &&
sleep 10s &&                                                                            #wait a bit for android device to be enlisted in adb
adb devices &&                                                                          #init adb damon
/entry/wait-for-device.sh &&                                                            #wait for android system to be loaded
echo 'android system init done...' &&
appium -U emulator-5554 --relaxed-security                                        #start appium server for this AVD