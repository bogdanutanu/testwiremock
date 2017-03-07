package com.lordbogdan.wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;


public class HeaderTransformation extends ResponseDefinitionTransformer {

    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource fileSource, Parameters parameters) {
        String tidHeader = request.getHeader("tid");
        return new ResponseDefinitionBuilder()
                .withHeader("tid", tidHeader).build();
    }

    public String getName() {
        return "HeaderTransformation";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
