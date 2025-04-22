        if (value == null) {
        }
        String emailAddress = (String) value;
        int index = emailAddress.indexOf('@');
        int length = emailAddress.length();
        if (index > 0 && index < length) {
            return null;
        }
        return MessageUtil
    }
