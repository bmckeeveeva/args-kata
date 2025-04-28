package args.before;

class DoubleHandler implements Handler {
    @Override
    public void parseSchemaElement(State state, char elementId, String elementTail) throws ArgsException {
        state.doubleArgs.put(elementId, 0.0);
    }

    @Override
    public boolean isSchemaElement(String elementTail) {
        return elementTail.equals("##");
    }

    @Override
    public void setArg(State state, char argChar) throws ArgsException {
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

    @Override
    public boolean isSupportedArg(State state, char argChar) {
        return state.doubleArgs.containsKey(argChar);
    }
}
