package myproject

import com.pulumi.kubernetes.core_v1.inputs.ContainerArgs
import com.pulumi.kubernetes.core_v1.inputs.ContainerPortArgs
import com.pulumi.kubernetes.core_v1.inputs.PodSpecArgs

fun getContainers(): PodSpecArgs.Builder = PodSpecArgs.builder().containers(
    ContainerArgs.builder()
        .name("nginx")
        .image("nginx")
        .ports(
            ContainerPortArgs.builder()
                .containerPort(80)
                .build()
        )
        .build()
)

