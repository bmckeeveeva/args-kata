package args.before;

class Util {
    static void validateSchemaElementId(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId)) {
            throw createArgsError(String.format("'%c' is not a valid argument name.", elementId),
                    ArgsErrorCode.INVALID_ARGUMENT_NAME);
        }
    }

    static ArgsException createArgsError(String msg, ArgsErrorCode code) {
        ArgsException err = new ArgsException(msg);
        err.setErrorCode(code);
        return err;
    }
}
