package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.ExchangeResponse;

public interface ExchangeFixerPort {
    ExchangeResponse getAllExchange();
}
