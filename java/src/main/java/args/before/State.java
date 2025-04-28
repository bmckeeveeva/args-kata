package args.before;

import java.util.Map;

public class State {
    String schema;
    String[] args;
    Map<Character, Boolean> booleanArgs;
    Map<Character, Integer> intArgs;
    Map<Character, String> stringArgs;
    Map<Character, Double> doubleArgs;
    int currentArgument;

    public State() {
    }

    boolean isStringArg(State state, char argChar) {
      return state.stringArgs.containsKey(argChar);
    }

    boolean isBooleanArg(State state, char argChar) {
      return state.booleanArgs.containsKey(argChar);
    }

    boolean isIntArg(char argChar) {
      return intArgs.containsKey(argChar);
    }

    boolean isDoubleArg(char argChar) {
        return doubleArgs.containsKey(argChar);
    }
}