package args.before;

class IntegerHandler implements Handler {
    @Override
    public void parseSchemaElement(State state, char elementId, String elementTail) throws ArgsException {
        state.intArgs.put(elementId, 0);
    }

    @Override
    public boolean isSchemaElement(String elementTail) {
        return elementTail.equals("#");
    }

    @Override
    public void setArg(State state, char argChar) throws ArgsException {
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

    @Override
    public boolean isSupportedArg(State state, char argChar) {
        return state.intArgs.containsKey(argChar);
    }
}
