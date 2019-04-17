This folder contains docker files to create Android emulator images. Commands below can be used to create ready-to-use docker containers.
Images are designed to work with UBUNTU 18 system.

Note, that due to NDA, some arts of images were altered, and some names might be meaningless.

Build core image, run from core folder:
docker build -t "company.app.core" .
Build android image, run from appium-docker folder:
docker build -t "company.app.v23" .
Run android image, run from anywhere on the system:
docker run --privileged -ti -p 1350:4723 company.app.v23