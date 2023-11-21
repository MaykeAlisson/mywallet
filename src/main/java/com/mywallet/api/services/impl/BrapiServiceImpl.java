package com.mywallet.api.services.impl;

import com.mywallet.api.domain.model.BrapiTicketModel;
import com.mywallet.api.services.BrapiService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static com.mywallet.api.provider.constrans.Environment.BRAPI_URL_FIND_TICKET;

@Service
@AllArgsConstructor
public class BrapiServiceImpl implements BrapiService {

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Override
    public Optional<BrapiTicketModel> findTicket(final String ticket) {
        final var httpEntity = new HttpEntity<>(new HttpHeaders());

        final String urlTemplate = UriComponentsBuilder
                .fromHttpUrl(String.format("%s/%s", getUrlFindTicket(), ticket))
                .queryParam("token", this.getTokenBrapi())
                .encode()
                .toUriString();

        final var response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                httpEntity,
                BrapiTicketModel.class
        );

        return Optional.of(response)
                .filter(ResponseEntity::hasBody)
                .map(ResponseEntity::getBody);
    }

    private String getTokenBrapi(){
        return environment.getProperty("brapiToken");
    }

    private String getUrlFindTicket() {
        return environment.getProperty(BRAPI_URL_FIND_TICKET, "");
    }
}
