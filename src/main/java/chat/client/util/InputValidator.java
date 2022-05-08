package chat.client.util;

public class InputValidator {

    public static boolean isInputValid(String input) {
        return !input.replaceAll(" ","").equals("");
    }
}
