package com.sicredi.testeapi;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;

import org.junit.BeforeClass;

public class MassaDados {
	
	@BeforeClass
    public static void urlbase() {
		baseURI = "http://localhost";
		port = 8080;
		basePath = "/api";

	}
	
	
	String cpf_sem_restricao = "03327740585";
	
	String cpf_com_restricao = "19626829001";
	
	String corpo_simulacao = "{\r\n"
			+ "  \"nome\": \"Sicrano de Tal\",\r\n"
			+ "  \"cpf\": 97093236020,\r\n"
			+ "  \"email\": \"sicrano@email.com\",\r\n"
			+ "  \"valor\": 12000,\r\n"
			+ "  \"parcelas\": 5,\r\n"
			+ "  \"seguro\": true\r\n"
			+ "}";
	
	String corpo_simulacao_erro = "{\r\n"
			+ "  \"nome\": \"Fulano de Tal\",\r\n"
			+ "  \"cpf\": 99999999999,\r\n"
			+ "  \"email\": \"email.com.br\",\r\n"
			+ "  \"valor\": 1200000000,\r\n"
			+ "  \"parcelas\": 1,\r\n"
			+ "  \"seguro\": true\r\n"
			+ "}";
	
	//Mesmo CPF informado na variável "corpo_simulacao
	String cpf_cadastrado = "97093236020";
	
	String corpo_simulacao_alterar = "{\r\n"
			+ "  \"nome\": \"Usuario Alterado\",\r\n"
			+ "  \"cpf\": 03327740585,\r\n"
			+ "  \"email\": \"email@email.com.br\",\r\n"
			+ "  \"valor\": 10000,\r\n"
			+ "  \"parcelas\": 5,\r\n"
			+ "  \"seguro\": false\r\n"
			+ "}";

	String cpf_inexistente = "123456789";
	
	int id_excluir = 11;

}
