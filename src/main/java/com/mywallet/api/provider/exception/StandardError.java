package com.mywallet.api.provider.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {

    private static final long serialVersionUID = 2290902674106698606L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
