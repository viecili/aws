package li.vieci.aws.lambda;

import org.apache.log4j.Logger;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeRequest;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

public class S3AutoDeployerLambda implements RequestHandler<S3Event, Void> {

	static final Logger logger = Logger.getLogger(S3AutoDeployerLambda.class);

	public static final String S3_WATCH_BUCKET_NAME = "lambda-auto-deployment";

	@Override
	public Void handleRequest(S3Event s3Event, Context context) {
		AWSLambdaAsync lambda = AWSLambdaAsyncClientBuilder.defaultClient();

		S3EventNotificationRecord s3Record = s3Event.getRecords().get(0);
		String key = s3Record.getS3().getObject().getKey();
		String bucket = s3Record.getS3().getBucket().getName();
		String objectVersion = s3Record.getS3().getObject().getVersionId();

		if (S3_WATCH_BUCKET_NAME.equals(bucket) && (key.endsWith(".zip") || key.endsWith(".jar"))) {
			UpdateFunctionCodeRequest ufcReq = new UpdateFunctionCodeRequest();
			ufcReq.setS3Key(key);
			ufcReq.setS3Bucket(bucket);
			ufcReq.setS3ObjectVersion(objectVersion);
			ufcReq.setFunctionName(key.substring(0, key.length()-4));

			AsyncHandler<UpdateFunctionCodeRequest, UpdateFunctionCodeResult> callback = new AsyncHandler<UpdateFunctionCodeRequest, UpdateFunctionCodeResult>() {

				@Override
				public void onError(Exception exception) {
					logger.error("Could not deploy Lambda Function automatically.", exception);
				}

				@Override
				public void onSuccess(UpdateFunctionCodeRequest request, UpdateFunctionCodeResult result) {
					logger.info("Lambda Function '" + request.getFunctionName() + "' deployed successfully - "
							+ result.getFunctionArn());
				}

			};
			logger.info("Deploying Lambda Function: " + ufcReq.getFunctionName());
//			lambda.updateFunctionCodeAsync(ufcReq, callback);
			try {
				UpdateFunctionCodeResult ufcRes = lambda.updateFunctionCode(ufcReq);
				callback.onSuccess(ufcReq, ufcRes);
			} catch (Exception e) {
				callback.onError(e);
			}
			
		} else {
			logger.debug("Skipping " + key + " in bucket " + bucket + " with version " + objectVersion);
		}

		return null;
	}

}