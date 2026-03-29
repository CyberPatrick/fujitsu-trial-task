package ee.fujitsu.boltfood.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpConfig {
    @Value("${http.connection-timeout}")
    private Integer connectionTimeout;
    @Value("${http.read-timeout}")
    private Integer readTimeout;

    @Bean
    public RestClient restClient() {
        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectionRequestTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }
}
