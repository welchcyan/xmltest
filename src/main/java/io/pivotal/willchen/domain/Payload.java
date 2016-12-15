package io.pivotal.willchen.domain;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by chenw13 on 12/12/2016.
 */
@Component
@XmlRootElement(name="payload")
public class Payload implements Serializable {

    private String payload;

    public Payload() {
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
