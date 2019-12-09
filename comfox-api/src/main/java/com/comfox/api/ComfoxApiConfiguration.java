package com.comfox.api;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class ComfoxApiConfiguration extends Configuration {
    @NotNull
    private String titanPropertiesFileLocation;

    public String getTitanPropertiesFileLocation() {
        return titanPropertiesFileLocation;
    }
}
