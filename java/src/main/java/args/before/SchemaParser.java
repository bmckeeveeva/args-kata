package args.before;

/**
 * Args parses CLI args.
 */
public class SchemaParser {
  public ArgsParser parse(String schema) throws ArgsException {
    State state = new State(schema);

    if (state.schema.isEmpty()) {
       return new ArgsParser(state);
    }

    parseSchema(state);

    return new ArgsParser(state);
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

    for (Handler handler : Handler.HANDLERS) {
      if (handler.isSchemaElement(elementTail)) {
        handler.parseSchemaElement(state, elementId, elementTail);
        return;
      }
    }
  }
}
