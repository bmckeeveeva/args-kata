package args.before;

public class StringHandler {
    public static void parseStringSchemaElement(State state, char elementId) {
        state.stringArgs.put(elementId, "");
    }

    public boolean isStringSchemaElement(String elementTail) {
        return elementTail.equals("*");
    }

    public static void setStringArg(State state, char argChar) throws ArgsException {
        state.currentArgument++;
        try {
            state.stringArgs.put(argChar, state.args[state.currentArgument]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw Util.createArgsError(String.format("Could not find string parameter for -%c.", argChar),
                    ArgsErrorCode.MISSING_STRING);
        }
    }
}
