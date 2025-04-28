package args.before;

public class BooleanHandler {
    public BooleanHandler() {
    }

    static void parseBooleanSchemaElement(State state, char elementId) {
        state.booleanArgs.put(elementId, false);
    }

    boolean isBooleanSchemaElement(String elementTail) {
        return elementTail.length() == 0;
    }

    static void setBooleanArg(State state, char argChar, boolean value) {
        state.booleanArgs.put(argChar, value);
    }
}