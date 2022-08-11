package myproject;

import static myproject.SupportKt.getContainers;

import com.pulumi.Pulumi;
import com.pulumi.core.Output;
import com.pulumi.kubernetes.apps_v1.Deployment;
import com.pulumi.kubernetes.apps_v1.DeploymentArgs;
import com.pulumi.kubernetes.apps_v1.inputs.DeploymentSpecArgs;
import com.pulumi.kubernetes.core_v1.inputs.PodTemplateSpecArgs;
import com.pulumi.kubernetes.meta_v1.inputs.LabelSelectorArgs;
import com.pulumi.kubernetes.meta_v1.inputs.ObjectMetaArgs;
import java.util.Map;

public class Main {

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
