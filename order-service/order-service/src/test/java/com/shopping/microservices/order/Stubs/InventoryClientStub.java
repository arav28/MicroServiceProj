package com.shopping.microservices.order.Stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {
    public static void stubInventoryCall(String skuCode, Integer qty){
        stubFor(get(urlEqualTo("/api/inventory?skuCode="+ skuCode + "&quantity=" + qty))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }
}
