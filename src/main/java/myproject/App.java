package myproject;

import com.pulumi.Pulumi;
import com.pulumi.core.Output;
import com.pulumi.kubernetes.apps_v1.Deployment;
import com.pulumi.kubernetes.apps_v1.DeploymentArgs;
import com.pulumi.kubernetes.apps_v1.inputs.DeploymentSpecArgs;
import com.pulumi.kubernetes.core_v1.inputs.ContainerArgs;
import com.pulumi.kubernetes.core_v1.inputs.ContainerPortArgs;
import com.pulumi.kubernetes.core_v1.inputs.PodSpecArgs;
import com.pulumi.kubernetes.core_v1.inputs.PodSpecArgs.Builder;
import com.pulumi.kubernetes.core_v1.inputs.PodTemplateSpecArgs;
import com.pulumi.kubernetes.meta_v1.inputs.LabelSelectorArgs;
import com.pulumi.kubernetes.meta_v1.inputs.ObjectMetaArgs;

import java.util.Map;

public class App {
    private static Builder getContainers() {
        return PodSpecArgs.builder()
            .containers(ContainerArgs.builder()
                .name("nginx")
                .image("nginx")
                .ports(ContainerPortArgs.builder()
                    .containerPort(80)
                    .build())
                .build());
    }

    private static Deployment getDeployment(Map labels) {
        Deployment deployment = new Deployment("nginx", DeploymentArgs.builder()
            .spec(DeploymentSpecArgs.builder()
                .selector(LabelSelectorArgs.builder()
                    .matchLabels(labels)
                    .build())
                .replicas(1)
                .template(PodTemplateSpecArgs.builder()
                    .metadata(ObjectMetaArgs.builder()
                        .labels(labels)
                        .build())
                    .spec(getContainers()
                        .build())
                    .build())

                .build())
            .build());
        return deployment;
    }

    public static void main(String[] args) {
        Pulumi.run(ctx -> {
            Map labels = Map.of("app", "nginx");

            Deployment deployment = getDeployment(labels);

            Output<Object> name = deployment.metadata()
                .applyValue(m -> m.orElseThrow().name().orElse(""));

            ctx.export("name", name);
        });
    }
}
