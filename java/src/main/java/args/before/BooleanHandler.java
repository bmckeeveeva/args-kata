package args.before;

class BooleanHandler implements Handler {
    public BooleanHandler() {
    }

    @Override
    public void parseSchemaElement(State state, char elementId, String elementTail) throws ArgsException {
        state.booleanArgs.put(elementId, false);
    }

    @Override
    public boolean isSchemaElement(String elementTail) {
        return elementTail.isEmpty();
    }

    @Override
    public void setArg(State state, char argChar) throws ArgsException {
        state.booleanArgs.put(argChar, true);
    }

    @Override
    public boolean isSupportedArg(State state, char argChar) {
        return state.booleanArgs.containsKey(argChar);
    }
}