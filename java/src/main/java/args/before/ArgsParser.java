package args.before;

/**
 * Args parses CLI args.
 */
public class ArgsParser {
  private final State state;

  ArgsParser(State state) {
    this.state = state;
  }

  public Args parse(String[] args) throws ArgsException {
    state.args = args;

    if (state.args.length == 0) {
      return state;
    }

    parseArguments(state);

    return state;
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
    for (Handler handler : Handler.HANDLERS) {
      if (handler.isSupportedArg(state, argChar)) {
        handler.setArg(state, argChar);
        return;
      }
    }
  }
}
