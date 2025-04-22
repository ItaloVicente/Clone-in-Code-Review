        return character;
    }

    /**
     * Makes the given character uppercase if it is a letter.
     *
     * @param keyCode
     *            The character to convert.
     * @return The uppercase equivalent, if any; otherwise, the character
     *         itself.
     */
    private static int toUpperCase(int keyCode) {
        if (keyCode > 0xFFFF) {
            return keyCode;
        }

        char character = (char) keyCode;
        return Character.isLetter(character) ? Character.toUpperCase(character)
                : keyCode;
    }

    /**
     * This class should never be instantiated.
     */
    private SWTKeySupport() {
    }
