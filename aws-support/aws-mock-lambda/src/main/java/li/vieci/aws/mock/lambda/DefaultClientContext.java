package li.vieci.aws.mock.lambda;

import java.util.Collections;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Client;
import com.amazonaws.services.lambda.runtime.ClientContext;

public class DefaultClientContext implements ClientContext {

	private static Client client = new DefaultClient();
	
	public Client getClient() {
		return client;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getCustom() {
		return Collections.EMPTY_MAP;
	}

	public Map<String, String> getEnvironment() {
		return Collections.unmodifiableMap(System.getenv());
	}

	private static class DefaultClient implements Client {

		public String getAppPackageName() {
			return "li.vieci.aws.junit.lambda";
		}

		public String getAppTitle() {
			return "aws-junit-lambda";
		}

		public String getAppVersionCode() {
			return "";
		}

		public String getAppVersionName() {
			return "";
		}

		public String getInstallationId() {
			return "";
		}
		
	}
}
