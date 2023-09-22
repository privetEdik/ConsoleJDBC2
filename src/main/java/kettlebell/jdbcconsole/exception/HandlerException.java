package kettlebell.jdbcconsole.exception;

public class HandlerException extends Exception{

	private static final long serialVersionUID = 1L;
	private Exception exception;
	private String massege;
	
	public HandlerException(Exception exception) {
		this.exception = exception;
	}
	
	public HandlerException(String massege) {
		this.massege = massege;
	}
	
	public void printError() {
		System.err.println(exception.getClass() + exception.getMessage());
	}
	
	public void pringMassege() {
		System.err.println(massege);
	}
}
