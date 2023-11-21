package com.mywallet.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiTicketModel implements Serializable {

    public List<Ticket> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Ticket(String symbol, String currency, String longName, Float regularMarketPrice) {
    }


}
