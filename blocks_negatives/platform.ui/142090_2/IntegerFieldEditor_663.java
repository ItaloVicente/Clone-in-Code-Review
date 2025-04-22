            getPreferenceStore().setValue(getPreferenceName(), i.intValue());
        }
    }

    /**
     * Returns this field editor's current value as an integer.
     *
     * @return the value
     * @exception NumberFormatException if the <code>String</code> does not
     *   contain a parsable integer
     */
    public int getIntValue() throws NumberFormatException {
