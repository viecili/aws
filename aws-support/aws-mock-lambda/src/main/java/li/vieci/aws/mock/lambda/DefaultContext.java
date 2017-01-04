package li.vieci.aws.mock.lambda;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.MDC;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * getMemoryLimitInMB(): Memory limit, in MB, you configured for the Lambda
 * function.
 * 
 * getFunctionName(): Name of the Lambda function that is running.
 * 
 * getFunctionVersion(): The Lambda function version that is executing. If an
 * alias is used to invoke the function, then getFunctionVersion will be the
 * version the alias points to.
 * 
 * getInvokedFunctionArn(): The ARN used to invoke this function. It can be
 * function ARN or alias ARN. An unqualified ARN executes the $LATEST version
 * and aliases execute the function version it is pointing to.
 * 
 * getAwsRequestId(): AWS request ID associated with the request. This is the ID
 * returned to the client called the invoke(). You can use the request ID for
 * any follow up enquiry with AWS support. Note that if AWS Lambda retries the
 * function (for example, in a situation where the Lambda function processing
 * Amazon Kinesis records throw an exception), the request ID remains the same.
 * 
 * getLogStreamName(): The CloudWatch log stream name for the particular Lambda
 * function execution. It can be null if the IAM user provided does not have
 * permission for CloudWatch actions.
 * 
 * getLogGroupName(): The CloudWatch log group name associated with the Lambda
 * function invoked. It can be null if the IAM user provided does not have
 * permission for CloudWatch actions.
 * 
 * getClientContext(): Information about the client application and device when
 * invoked through the AWS Mobile SDK. It can be null. Client context provides
 * client information such as client ID, application title, version name,
 * version code, and the application package name.
 * 
 * getIdentity(): Information about the Amazon Cognito identity provider when
 * invoked through the AWS Mobile SDK. It can be null.
 * 
 * getRemainingTimeInMillis(): Remaining execution time till the function will
 * be terminated, in milliseconds. At the time you create the Lambda function
 * you set maximum time limit, at which time AWS Lambda will terminate the
 * function execution. Information about the remaining time of function
 * execution can be used to specify function behavior when nearing the timeout.
 * 
 * getLogger(): Returns the Lambda logger associated with the Context object.
 * For more information, see Logging (Java).
 * 
 * @author viecili
 *
 */
public class DefaultContext implements Context {

	public static final int DEFAULT_TTL = 61000;
	
	private static ClientContext clientContext = new DefaultClientContext();
	
	private String awsRequestId;
	
	private LambdaLogger logger;
	
	private long timeout;
	
	public DefaultContext() {
		awsRequestId = "DC"+ThreadLocalRandom.current().nextLong(10000000000000l, 100000000000000l);
		MDC.put("AWSRequestId", awsRequestId);
		timeout = System.currentTimeMillis()+DEFAULT_TTL;
	}
	
	public String getAwsRequestId() {
		return awsRequestId;
	}

	public ClientContext getClientContext() {
		return clientContext;
	}

	public String getFunctionName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFunctionVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	public CognitoIdentity getIdentity() {
		return null;
	}

	public String getInvokedFunctionArn() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLogGroupName() {
		return null;
	}

	public String getLogStreamName() {
		return null;
	}

	public LambdaLogger getLogger() {
		if (logger == null) {
			logger = new DefaultLambdaLogger();
		}
		return logger;
	}

	public int getMemoryLimitInMB() {
		return (int) (Runtime.getRuntime().maxMemory()/(1024*1024));
	}

	public int getRemainingTimeInMillis() {
		return (int) (timeout - System.currentTimeMillis());
	}

}
