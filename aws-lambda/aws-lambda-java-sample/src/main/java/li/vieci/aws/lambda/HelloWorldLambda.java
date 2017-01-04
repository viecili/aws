package li.vieci.aws.lambda;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import li.vieci.aws.lambda.dto.Request;
import li.vieci.aws.lambda.dto.Response;

public class HelloWorldLambda implements RequestHandler<Request, Response> {

	static final Logger log = Logger.getLogger(HelloWorldLambda.class);
	
	public Response handleRequest(Request input, Context context) {
		log.info("received: " +input);
		return new Response("Hello "+input.getInput()+"!!");
	}

}
