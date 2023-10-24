package org.sdia.customerservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RefreshScope   //actuator if we do a commit we don't need to restart the server
public class CustomerConfigTestController {
    @Value("${GLOBAL.PARAMS.P1}")
    private String p1; //p1=global.params.p1
    @Value("${GLOBAL.PARAMS.P2}")
    private String p2;
    @Value("${customer.params.x}")
    private String x;
    @Value("${customer.params.y}")
    private String y;

    @GetMapping("/params")
    public Map<String,String> params(){
        return Map.of("p1",p1,"p2",p2,"x",x,"y",y);
    }
}
