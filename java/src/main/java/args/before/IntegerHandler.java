package args.before;

public class IntegerHandler {
    public static void parseIntegerSchemaElement(State state, char elementId) {
        state.intArgs.put(elementId, 0);
    }

    public boolean isIntegerSchemaElement(String elementTail) {
        return elementTail.equals("#");
    }

    public static void setIntArg(State state, char argChar) throws ArgsException {
        state.currentArgument++;
        String parameter = null;
        try {
            parameter = state.args[state.currentArgument];
            state.intArgs.put(argChar, Integer.parseInt(parameter));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw Util.createArgsError(String.format("Could not find integer parameter for -%c.", argChar),
                    ArgsErrorCode.MISSING_INTEGER);
        } catch (NumberFormatException e) {
            throw Util.createArgsError(
                    String.format("Argument -%c expects an integer but was '%s'.", argChar, parameter),
                    ArgsErrorCode.INVALID_INTEGER);
        }
    }
}
