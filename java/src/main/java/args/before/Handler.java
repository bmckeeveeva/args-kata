package args.before;

import java.util.List;

interface Handler {
    List<Handler> HANDLERS = List.of(
            new BooleanHandler(),
            new IntegerHandler(),
            new StringHandler(),
            new DoubleHandler(),
            new ErrorCaseHandler());

    void parseSchemaElement(State state, char elementId, String elementTail) throws ArgsException;
    boolean isSchemaElement(String elementTail);
    void setArg(State state, char argChar) throws ArgsException;
    boolean isSupportedArg(State state, char argChar);
}
