package com.company.app.tests.docker;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A docker container class, which is used to monitor container logs. Used to check if appium service is running on emulator
 */

public class DockerContainer {

    private final String cid;
    private final int exposedPort;
    private final AtomicBoolean containerStarted = new AtomicBoolean(false);

    public DockerContainer(String cid, int exposedPort) {
        this.cid = cid;
        this.exposedPort = exposedPort;
    }

    public void waitForLogEntry(String entry) throws InterruptedException {
        synchronized (this) {
            Runnable loggerThread = () -> DockerUtil.getDockerClient().logContainerCmd(cid)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .exec(new LogContainerResultCallback() {
                        @Override
                        public void onNext(Frame item) {
                            if (item.toString().contains(entry)) {
                                containerStarted.set(true);
                                notify();
                            } else {
                                System.out.println(item.toString());
                            }
                        }
                    });

            new Thread(loggerThread).start();

            while (!containerStarted.get()) {
                this.wait(500);
            }
        }
    }

    public void start() {
        DockerUtil.getDockerClient().startContainerCmd(this.cid).exec();
    }

    public void stop() {
        DockerUtil.getDockerClient().stopContainerCmd(this.cid).exec();
    }
}
