    static String parseUser(final String input) {
        if (!input.contains("@")) {
            return null;
        } else {
            String schemeRemoved = input.replaceAll("\\w+://", "");
            String username = schemeRemoved.replaceAll("@.*", "");
            return username;
        }
    }

