package args.before;

import java.util.List;

/**
 * Args parses CLI args.
 */
public class ArgsFactory {
  private final static List<Handler> HANDLERS = List.of(
          new BooleanHandler(),
          new IntegerHandler(),
          new StringHandler(),
          new DoubleHandler(),
          new ErrorCaseHandler());

  public Args parse(String schema, String[] args) throws ArgsException {
    State state = new State(schema, args);

    if (state.schema.isEmpty()) {
       return state;
    }

    parseSchema(state);

    if (state.args.length == 0) {
      return state;
    }

    parseArguments(state);

    return state;
  }

  private void parseSchema(State state) throws ArgsException {
    for (String element : state.schema.split(",")) {
      parseSchemaElement(state, element.trim());
    }
  }

  private void parseSchemaElement(State state, String element) throws ArgsException {
    char elementId = element.charAt(0);
    String elementTail = element.substring(1);
    Util.validateSchemaElementId(elementId);

    for (Handler handler : HANDLERS) {
      if (handler.isSchemaElement(elementTail)) {
        handler.parseSchemaElement(state, elementId, elementTail);
        return;
      }
    }
  }

  private void parseArguments(State state) throws ArgsException {
    for (state.currentArgument = 0; state.currentArgument < state.args.length; state.currentArgument++) {
      String arg = state.args[state.currentArgument];
      parseArgument(state, arg);
    }
  }

  private void parseArgument(State state, String arg) throws ArgsException {
    if (!arg.startsWith("-")) {
      throw Util.createArgsError(String.format("'%s' is not a valid argument format.", arg),
          ArgsErrorCode.INVALID_ARGUMENT_FORMAT);
    }
    parseElements(state, arg);
  }

  private void parseElements(State state, String arg) throws ArgsException {
    for (int i = 1; i < arg.length(); i++) {
      setArgument(state, arg.charAt(i));
    }
  }

  private void setArgument(State state, char argChar) throws ArgsException {
    for (Handler handler : HANDLERS) {
      if (handler.isSupportedArg(state, argChar)) {
        handler.setArg(state, argChar);
        return;
      }
    }
  }
}
