#See ../core/Dockerfile
# Should be isntalled during core image build:
#apt-get install apt-utils
#apt-get install qemu-kvm libvirt-bin libvirt-daemon-system libvirt-clients bridge-utils

FROM company.app.core:latest

LABEL maintainer "Alexey Osipenko <alexey.osipenko@github.com>"

#===============
# Expose Ports
#---------------
# 4723
#   Appium port
#===============
EXPOSE 4723

# Set up android env.
RUN sdkmanager emulator
RUN sdkmanager "system-images;android-23;default;x86_64"
RUN sdkmanager "tools"
RUN sdkmanager "platforms;android-23"
RUN avdmanager create avd -n emulator-5554 -k "system-images;android-23;default;x86_64" -b x86_64 -c 100M -d 7 -f

RUN mkdir /entry
WORKDIR /entry
COPY ./stub.apk /entry/stub.apk
COPY ./start-server.sh /entry/start-server.sh
COPY ./wait-for-device.sh /entry/wait-for-device.sh
RUN ["chmod", "+x", "/entry/start-server.sh"]
RUN ["chmod", "+x", "/entry/wait-for-device.sh"]

#Create mock folders structure
RUN mkdir /wiremock
RUN mkdir /wiremock/config
RUN mkdir /wiremock/config/mappings

#Get Wiremock
RUN wget -P /wiremock http://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/2.22.0/wiremock-standalone-2.22.0.jar

#place mappings
COPY ./mock/mappings.json /wiremock/config/mappings/mappings.json

ENTRYPOINT ["/entry/start-server.sh"]