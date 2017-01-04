package li.vieci.aws.lambda;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import li.vieci.aws.lambda.dto.Request;
import li.vieci.aws.lambda.dto.Response;
import li.vieci.aws.mock.lambda.DefaultContext;

public class HelloWorldLambdaTest {
	
	@Test
	public void testHandleRequest() {
		HelloWorldLambda lambda = new HelloWorldLambda();
		Request input = new Request("World");
		Response response = lambda.handleRequest(input , new DefaultContext());
		assertEquals("Hello World!!", response.getOutput());
	}

}
