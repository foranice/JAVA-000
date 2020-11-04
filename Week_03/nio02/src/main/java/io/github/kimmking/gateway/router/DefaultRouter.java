package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.Random;

public class DefaultRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        Random random = new Random();
        int n = random.nextInt(endpoints.size());
        return endpoints.get(n);

    }
}
