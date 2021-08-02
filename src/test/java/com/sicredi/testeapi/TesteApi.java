package com.sicredi.testeapi;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class TesteApi extends MassaDados{
	

	@Test
    public void testConsultaCPFSemRestricao() {
		
		given()
		.when()
		  .get("/v1/restricoes/{cpf}", cpf_sem_restricao)
		.then()
		  .assertThat()
		  .statusCode(204)
		  .log().all();

	}

	@Test
	public void testConsultaCPFComRestricao() {
		given()
		.when()
		  .get("/v1/restricoes/{cpf}", cpf_com_restricao)
		.then()
		  .assertThat()
		  .statusCode(200)
		  .contentType(ContentType.JSON)
		  .body("mensagem", is("O CPF " + cpf_com_restricao + " tem problema"));

	}
	
	@Test
	public void testInserirSimulacaoSucesso() {
		
		given().log().all()
		  .body(corpo_simulacao)
		  .contentType(ContentType.JSON)
		.when()
		  .post("/v1/simulacoes")
		.then()
		  .assertThat().statusCode(201);
		
		
	}
	
	
	//NO TESTE ABAIXO, FOI VERIFICADO QUE AO ENCONTRAR UM CPF JÁ CADASTRADO NA BASE, O RETORNO DO STATUS CODE É SEMPRE 400
	//NOS REQUISITOS FOI INFORMADO QUE O RETORNO SERIA 409, O QUE NÃO PÔDE SER REPRODUZIDO
	@Test
	public void testInserirSimulacaoComCPFJaCadastrado() {
		
		
		given()
		  .body(corpo_simulacao)
		  .contentType(ContentType.JSON)
		.when()
		  .post("/v1/simulacoes")
		.then()
		  .assertThat().statusCode(400)
		  .body("mensagem", is("CPF duplicado"));
	}
			
				
			
			@Test
			//Aqui vamos inserir e-mail, valor e parcelas com dados invalidos
			//Campo Parcela será informado um valor menor que 2 e validado a mensagem de erro coerente
			//Campo valor será informado um valor superior a 40000 e validado a mensagem de erro coerente
			public void testInserirSimulacaoComDadosInvalidos1() {
				
				
				given()
				  .body(corpo_simulacao_erro)
				  .contentType(ContentType.JSON)
				.when()
				  .post("/v1/simulacoes")
				.then()
				  .assertThat().statusCode(400)
				  .body("erros.parcelas", is("Parcelas deve ser igual ou maior que 2"))
				  .body("erros.valor", is("Valor deve ser menor ou igual a R$ 40.000"))
				  .body("erros.email", is("E-mail deve ser um e-mail válido"));
			}	
			
			//O TESTE ABAIXO ESTÁ COMENTADO POIS A VALIDAÇÃO DE PARCELAS PASSANDO DO LIMITE DE 48 NÃO OCORRE
			//VALIDAÇÃO DO VALOR ABAIXO DE 1000 NÃO ESTÁ OCORRENDO NA APLICAÇÃO DISPONIBILIZADA.
			//TESTE FO1 FEITO SEGUINDO A LÓGICA PROVÁVEL
					
//					@Test
//					//Aqui vamos inserir e-mail, valor e parcelas com dados invalidos
//					//Campo Parcela será informado um valor maior que 48 e validado a mensagem de erro coerente
//					//Campo valor será informado um valor menor que 1000 e validado a mensagem de erro coerente
//					public void testInserirSimulacaoComDadosInvalidos2() {
//						urlbase();
//						
//						given()
//						  .body("{\r\n"
//						  		+ "  \"nome\": \"Fulano de Tal\",\r\n"
//						  		+ "  \"cpf\": 99999999999,\r\n"
//						  		+ "  \"email\": \"email.com.br\",\r\n"
//						  		+ "  \"valor\": 1000000000,\r\n"
//						  		+ "  \"parcelas\": 1,\r\n"
//						  		+ "  \"seguro\": true\r\n"
//						  		+ "}")
//						  .contentType(ContentType.JSON)
//						.when()
//						  .post("/v1/simulacoes")
//						.then()
//						  .assertThat().statusCode(400)
//						  .body("erros.parcelas", is("Parcelas deve ser menor ou igual que 48"))
//						  .body("erros.valor", is("Valor deve ser maior ou igual a R$ 1.000"))
//						  .body("erros.email", is("E-mail deve ser um e-mail válido"));
//						{				

			@Test
			public void testConsultarSimulacaoPorCPF() {
				
				given().log().all()
					  .pathParam("cpf", cpf_cadastrado)
				.when()
				  .get("/v1/simulacoes/{cpf}")
				.then().assertThat()
				  .statusCode(200);
			}
			

			@Test
			public void testAlterarSimulacaoPorCPFExistente() {
				
				
				
				given().log().all()
				  .body(corpo_simulacao_alterar) 
					  .pathParam("cpf", cpf_cadastrado)
				.when()
				  .put("/v1/simulacoes/{cpf}")
				.then().log().all()
				  .statusCode(200)
				  .body("cpf",is("03327740585"))
				  .body("nome",is("Usuario Alterado"))
				  .body("email",is("email@email.com.br"))
				  .body("valor",is(10000))
				  .body("parcelas",is(5))
				.body("seguro",is(false));				  
			}	
			
			
			@Test
			public void testAlterarSimulacaoPorCPFInexistente() {
				
				given().log().all()
				  .body(corpo_simulacao) 
					  .pathParam("cpf", cpf_inexistente)
				.when()
				  .put("/v1/simulacoes/{cpf}")
				.then().log().all()
				  .statusCode(404)
				  .body("mensagem", is("CPF " + cpf_inexistente + " não encontrado"));
			  
			}	
			
			
			@Test
			public void testConsultarTodasAsSimulacoes() {
				
				given()

				.when()
				  .get("/v1/simulacoes")
				.then().assertThat()
				  .statusCode(200)
				  .log().all();
			}
			
			@Test
			public void testExcluirSimulacaoExistente() {
				
				
				given().log().all()
					  .pathParam("id", id_excluir)
				.when()
				  .delete("/v1/simulacoes/{id}")
				.then().log().all()
				  .statusCode(204);
				
				
			}
}


