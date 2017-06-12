package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardInput {
	private static BufferedReader _in  = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Displays the specific prompt and shows what is typed into the keyboard by the user.
	 * 
	 * @param prompt The prompt to print.
	 * @return A string, or null if there is a problem reading the input.
	 */
	public static String readFromKeyboard(String prompt) {
		try {
			System.out.print(prompt);
			String read = _in.readLine();
			System.err.println("Got [" + read + "]");
			return read;
		} catch (IOException e) {
			System.out.println("%%Message: problem getting input");
		}
		return null;
	}
	/**
	 * Read an integer from a fixed range of numbers from the command line. If valid input is not given, the method will continue to ask for valid
	 * input until it is explicitly cancelled. 
	 *
	 * @param prompt Message displayed when requesting the user integer input
	 * @param lower The lower bound of the range (inclusive)
	 * @param upper The upper bound of the range (inclusive)
	 * @param cancelResult What value to return if the attempt at input is cancelled
	 * @param cancelStr What string the user should enter to cancel the input
	 * @return Either cancelResult or an integer in the range [lower..upper] (inclusive).
	 */
	public static int readInteger(String prompt, int lower, int upper, int cancelResult, String cancelStr) {
		int result;
		while (true) {
			String input = KeyboardInput.readFromKeyboard(prompt);
			//System.err.println("Got {" + input + "}");
			if (input.equals(cancelStr)) {
				return cancelResult;
			}
			try {
				result = Integer.parseInt(input);
				if (result < lower || result > upper) {
					System.out.println("%%Message: `" + input + "' must be between " + 
							lower + " and " + upper + ", or '" + cancelStr + "' to cancel");
				} else {
					return result;
				}
			} catch (NumberFormatException nfex) {
				System.out.println("%%Message: `" + input + "' is not a valid response.\n" +
						"Input must be an integer between '" + lower + "' and '" + upper + "'");
			}
		}
	}

}