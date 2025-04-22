        String keyDelimiter = getKeyDelimiter();

        SortedSet modifierKeys = new TreeSet(getModifierKeyComparator());
        modifierKeys.addAll(keyStroke.getModifierKeys());
        StringBuilder stringBuffer = new StringBuilder();
        Iterator modifierKeyItr = modifierKeys.iterator();
        while (modifierKeyItr.hasNext()) {
            stringBuffer.append(format((ModifierKey) modifierKeyItr.next()));
            stringBuffer.append(keyDelimiter);
        }

        NaturalKey naturalKey = keyStroke.getNaturalKey();
        if (naturalKey != null) {
            stringBuffer.append(format(naturalKey));
        }

        return stringBuffer.toString();

    }

    /**
     * An accessor for the delimiter you wish to use between keys. This is used
     * by the default format implementations to determine the key delimiter.
     *
     * @return The delimiter to use between keys; should not be <code>null</code>.
     */
    protected abstract String getKeyDelimiter();

    /**
     * An accessor for the delimiter you wish to use between key strokes. This
     * used by the default format implementations to determine the key stroke
     * delimiter.
     *
     * @return The delimiter to use between key strokes; should not be <code>null</code>.
     */
    protected abstract String getKeyStrokeDelimiter();

    /**
     * An accessor for the comparator to use for sorting modifier keys. This is
     * used by the default format implementations to sort the modifier keys
     * before formatting them into a string.
     *
     * @return The comparator to use to sort modifier keys; must not be <code>null</code>.
     */
    protected abstract Comparator getModifierKeyComparator();
