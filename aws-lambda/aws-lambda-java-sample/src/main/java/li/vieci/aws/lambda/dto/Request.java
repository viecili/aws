package li.vieci.aws.lambda.dto;

public class Request {

	private String input;

	public Request() {
	}

	public Request(String input) {
		this.input = input;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

}
