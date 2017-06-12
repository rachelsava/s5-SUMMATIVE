package utility;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * IO supports a useful means to get input from the Keyboard,
 * and to record what input is provided, meaning it can be used as a replacement
 * for other scanners in some applications. 
 */
public class MockIO implements IO {
	/**
	 * Set this to true to print output showing various states of the IO process,
	 * particularly useful for figuring out why tests have failed.
	 */
	private static final boolean DEBUG = false;
	
	/**
	 * Used to keep a record of the inputs provided
	 */
	private List<String> _inputs;
	
	//
	/**
	 * Ask for input from the current input location, displaying the specified prompt
	 * to the current output location. 
	 * @param prompt A string prompting for the input.
	 * @return A string as supplied from the current input location.
	 */
	public String readFromKeyboard(String prompt) {
		String input = KeyboardInput.readFromKeyboard(prompt);
		return input;
	}
	/**
	 * Ask for an integer input from the current input location, displaying the specified prompt
	 * plus the cancelStr to the current output location, and requiring that the input be from 
	 * the range [lower..upper]. If the supplied input matches the cancelStr, then the value
	 * cancelResult will be returned. If the supplied input is not in the required range, the
	 * request for input will be repeated.
	 * @param prompt
	 * @param lower
	 * @param upper
	 * @param cancelResult
	 * @param cancelStr
	 * @return 
	 */
	public int readInteger(String prompt, int lower, int upper, int cancelResult, String cancelStr) {
		int result;
		while (true) {
			String input = readFromKeyboard(prompt);
			if (DEBUG) {
				System.err.println("Got input {" + input + "}");
			}
			if (input.equals(cancelStr)) {
				result = cancelResult;
				break;
			}
			try {
				result = Integer.parseInt(input);
				// If we get here, we must have a valid int
				if (result < lower || result > upper) {
					System.out.println("%%Message: `" + input + "' must be between " + 
							lower + " and " + upper + ", or '" + cancelStr + "' to cancel");
				} else {
					break;
				}
			} catch (NumberFormatException nfex) {
				System.out.println("%%Message: `" + input + "' is not a valid response.\n" +
						"Input must be an integer between '" + lower + "' and '" + upper + "'");
			}
		}
		return result;
	}
	
	public void println(String str) {
		System.out.println(str);
	}
	/**
	 * Start recording inputs in Vector.
	 */
	public void record() {
		_inputs = new Vector<String>();
	

	
		
		for (String input: _inputs) {
			System.out.println(input);
		}
	}
	@Override
	public void print(String str) {
		// TODO Auto-generated method stub
		
	}
	}