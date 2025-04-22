        long longevityValue = validateLongTextEntry(longevityText, DAY_LENGTH);
        int maxFileStates = validateMaxFileStates();
        long maxStateSize = validateMaxFileStateSize();
        boolean applyPolicy = applyPolicyButton.getSelection();
        if (longevityValue == FAILED_VALUE || maxFileStates == FAILED_VALUE
                || maxStateSize == FAILED_VALUE) {
