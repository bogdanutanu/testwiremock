# Test Wiremock

## Objective

The aim of this project is to test the extensions WireMock provides to introduce some behaviour,
specifically in order to return the same value as received for a specific header.
As specified in the documentation for [extending WireMock](http://wiremock
.org/docs/extending-wiremock/), we use a *ResponseDefinitionTransformer* to achieve our goal.

## Versions

Wiremock 2.5.1

Java 1.8.0_121
Maven 3.3.9