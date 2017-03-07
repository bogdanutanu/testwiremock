package com.lordbogdan.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TransformingTest {

    private static WireMockServer wireMockServer;

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    @BeforeClass
    public static void startUp() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8080)
                .extensions(new HeaderTransformation()));
        wireMockServer.start();
    }

    @AfterClass
    public static void shutDown() {
        wireMockServer.stop();
    }

    @Test
    public void testTransformation() throws Exception {
        stub();
        HttpPost post = new HttpPost();
        post.setURI(new URI("http://localhost:8080/end"));
        post.addHeader("tid", "whatever");

        HttpResponse response = httpClient.execute(post);

        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        assertTrue(response.getFirstHeader("tid").getValue().equals("whatever"));
    }

    private void stub() {
        wireMockServer.stubFor(post(urlEqualTo("/end"))
                .withHeader("tid", matching("^(?!\\s*$).+"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withTransformers("HeaderTransformation")));
    }

}
