package args.before;

class StringHandler implements Handler {
    @Override
    public void parseSchemaElement(State state, char elementId, String elementTail) throws ArgsException {
        state.stringArgs.put(elementId, "");
    }

    @Override
    public boolean isSchemaElement(String elementTail) {
        return elementTail.equals("*");
    }

    @Override
    public void setArg(State state, char argChar) throws ArgsException {
        state.currentArgument++;
        try {
            state.stringArgs.put(argChar, state.args[state.currentArgument]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw Util.createArgsError(String.format("Could not find string parameter for -%c.", argChar),
                    ArgsErrorCode.MISSING_STRING);
        }
    }

    @Override
    public boolean isSupportedArg(State state, char argChar) {
        return state.stringArgs.containsKey(argChar);
    }
}
