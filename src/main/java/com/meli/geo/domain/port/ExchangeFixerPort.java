package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.ExchangeResponse;
import com.meli.geo.infrastructure.adapter.externalservices.ExchangeFixer;

public interface ExchangeFixerPort {
    ExchangeResponse getAllExchange();
}
