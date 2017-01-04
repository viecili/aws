package li.vieci.aws.mock.lambda;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class DefaultLambdaLogger implements LambdaLogger {

	static final Logger log = Logger.getLogger(DefaultLambdaLogger.class);
	
	public void log(String arg0) {
		log.info(arg0);
	}

}
