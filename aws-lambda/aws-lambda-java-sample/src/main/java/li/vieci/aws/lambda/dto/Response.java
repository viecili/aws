package li.vieci.aws.lambda.dto;

public class Response {

	private String output;

	public Response() {
	}
	
	public Response(String output) {
		this.output = output;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
