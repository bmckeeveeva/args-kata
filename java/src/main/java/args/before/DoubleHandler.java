package args.before;

public class DoubleHandler {
    public static void parseDoubleSchemaElement(State state, char elementId) {
        state.doubleArgs.put(elementId, 0.0);
    }

    public boolean isDoubleSchemaElement(String elementTail) {
        return elementTail.equals("##");
    }

    public static void setDoubleArg(State state, char argChar) throws ArgsException {
        state.currentArgument++;
        String parameter = null;
        try {
            parameter = state.args[state.currentArgument];
            state.doubleArgs.put(argChar, Double.parseDouble(parameter));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw Util.createArgsError(String.format("Could not find double parameter for -%c.", argChar),
                    ArgsErrorCode.MISSING_DOUBLE);
        } catch (NumberFormatException e) {
            throw Util.createArgsError(
                    String.format("Argument -%c expects a double but was '%s'.", argChar, parameter),
                    ArgsErrorCode.INVALID_DOUBLE);
        }
    }

    public static boolean isDoubleArg(State state, char argChar) {
        return state.doubleArgs.containsKey(argChar);
    }
}
