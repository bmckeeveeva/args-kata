package args.before;

interface Handler {
    void parseSchemaElement(State state, char elementId, String elementTail) throws ArgsException;
    boolean isSchemaElement(String elementTail);
    void setArg(State state, char argChar) throws ArgsException;
    boolean isSupportedArg(State state, char argChar);
}
