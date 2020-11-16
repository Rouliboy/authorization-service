package com.jvi.swat.ms.authorization.service.definition;

import com.jvi.swat.ms.authorization.model.JwtToken;

public interface SecurityService {

  JwtToken generateToken(final String username, final String password);

}
