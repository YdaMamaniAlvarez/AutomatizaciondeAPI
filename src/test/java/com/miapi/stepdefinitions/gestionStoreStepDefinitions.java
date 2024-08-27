package com.miapi.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;

import java.util.*;

import static io.restassured.response.Response.*;
import static org.junit.Assert.*;

import io.restassured.response.Response;

public class gestionStoreStepDefinitions {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private Response response;
    private Map<String, Object> orderDetails;

    @Given("que tengo los detalles del pedido de una mascota")
    public void prepararDetallesPedido(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        orderDetails = new HashMap<>(data.get(0));
        orderDetails.put("shipDate", "2024-08-27T18:40:48.246Z");
        orderDetails.put("complete", true);
    }

    @When("realizo una solicitud POST a {string} con los detalles del pedido")
    public void realizarSolicitudPost(String endpoint) {
        response = SerenityRest.given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(orderDetails)
                .when()
                .post(endpoint); //<---Post
    }

    @Then("la respuesta debe tener un código de estado {string}")
    public void verificarCodigoEstado(String expectedStatusCode) {
        int statusCode = Integer.parseInt(expectedStatusCode);
        assertEquals("El código de estado no coincide", statusCode, response.getStatusCode());//<--assert
    }

    @And("la respuesta debe contener los detalles del pedido creado")
    public void verificarDetallesPedidoCreado() {
        Map<String, Object> responseBody = response.as(Map.class);

        assertEquals("El ID del pedido no coincide",
                Integer.parseInt(orderDetails.get("id").toString()), responseBody.get("id"));
        assertEquals("El ID de la mascota no coincide",
                Integer.parseInt(orderDetails.get("petId").toString()), responseBody.get("petId"));
        assertEquals("La cantidad no coincide",
                Integer.parseInt(orderDetails.get("quantity").toString()), responseBody.get("quantity"));
        assertEquals("El estado no coincide",
                orderDetails.get("status"), responseBody.get("status"));

        assertNotNull("La fecha de envío no debería ser nula", responseBody.get("shipDate"));
        assertTrue("El pedido debería estar marcado como completo", (Boolean) responseBody.get("complete"));
    }

    @And("el estado del pedido debe ser {string}")
    public void verificarEstadoPedido(String expectedStatus) {
        String actualStatus = response.path("status");
        assertEquals("El estado del pedido no coincide con el esperado", expectedStatus, actualStatus);
    }

    @Given("que existe un pedido con ID {string} en el sistema")
    public void existePedidoConId(String orderId) {
        // En un escenario real, aquí podríamos verificar si el pedido existe
        // o crearlo si es necesario. Por ahora, asumimos que existe.
    }

    @When("realizo una solicitud GET a {string}")
    public void realizarSolicitudGet(String endpoint) {
        response = SerenityRest.given()
                .baseUri(BASE_URL)
                .when()
                .get(endpoint);//<---Get
    }

    @And("la respuesta debe contener los detalles del pedido con ID {string}")
    public void verificarDetallesPedido(String orderId) {
        Map<String, Object> responseBody = response.as(Map.class);

        assertEquals("El ID del pedido no coincide con el esperado",
                Integer.parseInt(orderId), responseBody.get("id"));
        assertNotNull("El ID de la mascota no debería ser nulo", responseBody.get("petId"));
        assertNotNull("La cantidad no debería ser nula", responseBody.get("quantity"));
        assertNotNull("El estado no debería ser nulo", responseBody.get("status"));
        assertNotNull("La fecha de envío no debería ser nula", responseBody.get("shipDate"));
        assertNotNull("El campo 'complete' no debería ser nulo", responseBody.get("complete"));
    }
}