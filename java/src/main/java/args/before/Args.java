package args.before;

import java.util.HashMap;

/**
 * Args parses CLI args.
 */
public class Args {
  private final State state = new State();
  private final BooleanHandler booleanHandler = new BooleanHandler();
  private final IntegerHandler integerHandler = new IntegerHandler();
  private final StringHandler stringHandler = new StringHandler();
  private final DoubleHandler doubleHandler = new DoubleHandler();

  /**
   * Args Constructor.
   *
   * @param schema Schema of the args, see project README for more details
   * @param args   CLI args to parse
   * @throws ArgsException if there is a problem parsing the args
   */
  public Args(String schema, String[] args) throws ArgsException {
    this.state.schema = schema;
    this.state.args = args;
    this.state.booleanArgs = new HashMap<>();
    this.state.intArgs = new HashMap<>();
    this.state.stringArgs = new HashMap<>();
    this.state.doubleArgs = new HashMap<>();
    this.state.currentArgument = 0;
    parse();
  }

  private void parse() throws ArgsException {
    if (state.schema.length() == 0) {
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
    if (booleanHandler.isBooleanSchemaElement(elementTail)) {
      BooleanHandler.parseBooleanSchemaElement(state, elementId);
    } else if (stringHandler.isStringSchemaElement(elementTail)) {
      StringHandler.parseStringSchemaElement(state, elementId);
    } else if (integerHandler.isIntegerSchemaElement(elementTail)) {
      IntegerHandler.parseIntegerSchemaElement(state, elementId);
    } else if (doubleHandler.isDoubleSchemaElement(elementTail)) {
      DoubleHandler.parseDoubleSchemaElement(state, elementId);
    } else {
      throw Util.createArgsError(String.format("'%s' is not a valid argument format.", elementTail),
          ArgsErrorCode.INVALID_ARGUMENT_FORMAT);
    }
  }

  private boolean parseArguments() throws ArgsException {
    for (state.currentArgument = 0; state.currentArgument < state.args.length; state.currentArgument++) {
      String arg = state.args[state.currentArgument];
      parseArgument(arg);
    }
    return true;
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

  private boolean setArgument(char argChar) throws ArgsException {
    if (state.isBooleanArg(state, argChar)) {
      BooleanHandler.setBooleanArg(state, argChar, true);
    } else if (state.isStringArg(state, argChar)) {
      StringHandler.setStringArg(state, argChar);
    } else if (state.isIntArg(argChar)) {
      IntegerHandler.setIntArg(state, argChar);
    } else if (DoubleHandler.isDoubleArg(state, argChar)) {
      DoubleHandler.setDoubleArg(state, argChar);
    } else {
      throw Util.createArgsError(String.format("Argument -%c unexpected.", argChar),
          ArgsErrorCode.UNEXPECTED_ARGUMENT);
    }
    return true;
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
