package com.jvi.swat.ms.authorization.service.definition;

import com.jvi.swat.ms.authorization.model.JwtToken;
import com.jvi.swat.ms.authorization.model.JwtTokenUserProps;

public interface TokenService {

  JwtToken generate(final JwtTokenUserProps jwtTokenUserProps);
}
