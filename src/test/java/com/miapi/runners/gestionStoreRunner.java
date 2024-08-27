package com.miapi.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "com.miapi.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports"},
        tags = "@crearPedidodeMascota or @consultarPedidoporId"
)
public class gestionStoreRunner {
}