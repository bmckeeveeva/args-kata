package args.before;

import java.util.HashMap;
import java.util.Map;

class State implements Args {
    String schema;
    String[] args;
    Map<Character, Boolean> booleanArgs;
    Map<Character, Integer> intArgs;
    Map<Character, String> stringArgs;
    Map<Character, Double> doubleArgs;
    int currentArgument;

    State(String schema, String[] args) {
        this.schema = schema;
        this.args = args;
        this.booleanArgs = new HashMap<>();
        this.intArgs = new HashMap<>();
        this.stringArgs = new HashMap<>();
        this.doubleArgs = new HashMap<>();
        this.currentArgument = 0;
    }

    @Override
    public String getString(char arg) {
        return stringArgs.get(arg);
    }

    @Override
    public int getInt(char arg) {
        return intArgs.get(arg);
    }

    @Override
    public boolean getBoolean(char arg) {
        return booleanArgs.get(arg);
    }

    @Override
    public double getDouble(char arg) {
        return doubleArgs.get(arg);
    }
}