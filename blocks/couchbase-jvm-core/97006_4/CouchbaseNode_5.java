    private String logIdent() {
        if (alternate != null) {
            return "[" + hostname.nameAndAddress() + " (" + alternate.nameAndAddress() + ")]: ";
        } else {
            return "[" + hostname.nameAndAddress() + "]: ";
        }
