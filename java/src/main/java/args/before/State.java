package args.before;

import java.util.HashMap;
import java.util.Map;

public class State {
    String schema;
    String[] args;
    Map<Character, Boolean> booleanArgs;
    Map<Character, Integer> intArgs;
    Map<Character, String> stringArgs;
    Map<Character, Double> doubleArgs;
    int currentArgument;

    public State(String schema, String[] args) {
        this.schema = schema;
        this.args = args;
        this.booleanArgs = new HashMap<>();
        this.intArgs = new HashMap<>();
        this.stringArgs = new HashMap<>();
        this.doubleArgs = new HashMap<>();
        this.currentArgument = 0;
    }
}