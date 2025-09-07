package args.before;

class ErrorCaseHandler implements Handler {
    @Override
    public void parseSchemaElement(State state, char elementId, String elementTail)  throws ArgsException {
        throw Util.createArgsError(String.format("'%s' is not a valid argument format.", elementTail),
                ArgsErrorCode.INVALID_ARGUMENT_FORMAT);
    }

    @Override
    public boolean isSchemaElement(String elementTail) {
        return true;
    }

    @Override
    public void setArg(State state, char argChar) throws ArgsException {
        throw Util.createArgsError(String.format("Argument -%c unexpected.", argChar),
                ArgsErrorCode.UNEXPECTED_ARGUMENT);
    }

    @Override
    public boolean isSupportedArg(State state, char argChar) {
        return true;
    }
}
