package args.before;

import java.util.HashMap;
import java.util.List;

/**
 * Args parses CLI args.
 */
public class Args {
  private final static List<Handler> HANDLERS = List.of(
          new BooleanHandler(),
          new IntegerHandler(),
          new StringHandler(),
          new DoubleHandler(),
          new ErrorCaseHandler());

  private final State state;

  /**
   * Args Constructor.
   *
   * @param schema Schema of the args, see project README for more details
   * @param args   CLI args to parse
   * @throws ArgsException if there is a problem parsing the args
   */
  public Args(String schema, String[] args) throws ArgsException {
    this.state = new State(schema, args);
    parse();
  }

  private void parse() throws ArgsException {
    if (state.schema.isEmpty()) {
      return;
    }

    parseSchema();

    if (state.args.length == 0) {
      return;
    }

    parseArguments();
  }

  private void parseSchema() throws ArgsException {
    for (String element : state.schema.split(",")) {
      parseSchemaElement(element.trim());
    }
  }

  private void parseSchemaElement(String element) throws ArgsException {
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

  private void parseArguments() throws ArgsException {
    for (state.currentArgument = 0; state.currentArgument < state.args.length; state.currentArgument++) {
      String arg = state.args[state.currentArgument];
      parseArgument(arg);
    }
  }

  private void parseArgument(String arg) throws ArgsException {
    if (!arg.startsWith("-")) {
      throw Util.createArgsError(String.format("'%s' is not a valid argument format.", arg),
          ArgsErrorCode.INVALID_ARGUMENT_FORMAT);
    }
    parseElements(arg);
  }

  private void parseElements(String arg) throws ArgsException {
    for (int i = 1; i < arg.length(); i++) {
      setArgument(arg.charAt(i));
    }
  }

  private void setArgument(char argChar) throws ArgsException {
    for (Handler handler : HANDLERS) {
      if (handler.isSupportedArg(state, argChar)) {
        handler.setArg(state, argChar);
        return;
      }
    }
  }

  public String getString(char arg) {
    return state.stringArgs.get(arg);
  }

  public int getInt(char arg) {
    return state.intArgs.get(arg);
  }

  public boolean getBoolean(char arg) {
    return state.booleanArgs.get(arg);
  }

  public double getDouble(char arg) {
    return state.doubleArgs.get(arg);
  }
}
