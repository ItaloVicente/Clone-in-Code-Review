        String result = PATH_PREFIX;
        if (!empty(domain)) {
            result += "/" + domain;
        }
        if (!empty(userId)) {
            result += "/" + userId;
        }
        return result;
    }

    private static boolean empty(final String input) {
        return input == null || input.isEmpty();
