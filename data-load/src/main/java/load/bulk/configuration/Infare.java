package load.bulk.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "infare")
public class Infare {
    private List<String> inputUrls;

    public List<String> getInputUrls() {
        return inputUrls;
    }

    public void setInputUrls(List<String> inputUrls) {
        this.inputUrls = inputUrls;
    }
}
