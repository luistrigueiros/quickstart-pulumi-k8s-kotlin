package myproject

import com.pulumi.kubernetes.core_v1.inputs.ContainerArgs
import com.pulumi.kubernetes.core_v1.inputs.ContainerPortArgs
import com.pulumi.kubernetes.core_v1.inputs.PodSpecArgs
import com.pulumi.kubernetes.core_v1.inputs.PodTemplateSpecArgs
import com.pulumi.kubernetes.meta_v1.inputs.ObjectMetaArgs

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

fun getTemplate(labels: Map<String, String>): PodTemplateSpecArgs =
    PodTemplateSpecArgs.builder()
        .metadata(
            ObjectMetaArgs.builder()
                .labels(labels)
                .build()
        )
        .spec(
            getContainers()
                .build()
        ).build()



