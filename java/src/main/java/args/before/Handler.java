package args.before;

interface Handler {
    void parseSchemaElement(State state, char elementId);
    boolean isSchemaElement(String elementTail);
    void setArg(State state, char argChar) throws ArgsException;
}
