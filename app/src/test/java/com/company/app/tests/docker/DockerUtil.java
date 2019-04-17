package com.company.app.tests.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

/**
 * A utility class. Currently designed only to start docker images before tests
 */

public class DockerUtil {

    private final static DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    private final static DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

    public static DockerClient getDockerClient() {
        return dockerClient;
    }

    //    imageId = company.app.v23:latest
    //    externalPort = 1350
    public static String createDockerContainer(String imageId, int externalPort) {
        return dockerClient
                .createContainerCmd(imageId)
                .withCmd("--privileged")
                .withHostConfig(new HostConfig()
                        .withPrivileged(true)
                        .withPortBindings(new PortBinding(Ports.Binding.bindPort(externalPort), ExposedPort.tcp(4723)))
                ).exec().getId();
    }
}
