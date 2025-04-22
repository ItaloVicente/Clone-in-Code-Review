        super.doTearDown();

        shell.close();
        shell.dispose();

        shell = null;
        text = null;
    }

    /**
     * Tests that the limiting facility on KeySequenceText allows an arbitrary
     * number of key strokes, when the the limit is set to "infinite". In this
     * case, we will use a six stroke sequence.
     *
     * @throws ParseException
     *             If the test sequence cannot be parsed.
     */
    public void testInfiniteStrokes() throws ParseException {
        KeySequence keySequence = KeySequence.getInstance(keySequenceText);
        text.setKeyStrokeLimit(KeySequenceText.INFINITE);
        text.setKeySequence(keySequence);
        assertEquals(
                "Infinite limit but sequence changed.", keySequence, text.getKeySequence()); //$NON-NLS-1$
    }

    /**
     * Tests that inserting a key sequence of matching length causes no change,
     * but inserted a key sequence of one greater does cause a change --
     * specifically truncation.
     *
     * @throws ParseException
     *             If the test sequences cannot be parsed.
     */
    public void testTruncation() throws ParseException {
        final int length = 4;
        text.setKeyStrokeLimit(length);

        KeySequence matchingKeySequence = KeySequence
                .getInstance(matchingKeySequenceText);
        text.setKeySequence(matchingKeySequence);
        assertEquals(
                "Limit of four change four stroke sequence.", matchingKeySequence, text.getKeySequence()); //$NON-NLS-1$

        KeySequence longerKeySequence = KeySequence
                .getInstance(longerKeySequenceText);
        text.setKeySequence(longerKeySequence);
        assertEquals(
                "Limit of four did not truncate to four.", length, text.getKeySequence().getKeyStrokes().length); //$NON-NLS-1$
    }

    /**
     * Tests that a zero-length stroke can be inserted into the KeySequenceText --
     * regardless of whether the stroke limit is some positive integer or
     * infinite.
     */
    public void testZeroStroke() {
        KeySequence zeroStrokeSequence = KeySequence.getInstance();

        text.setKeyStrokeLimit(4);
        text.setKeySequence(zeroStrokeSequence);
        assertEquals(
                "Limit of four changed zero stroke sequence.", zeroStrokeSequence, text.getKeySequence()); //$NON-NLS-1$

        text.setKeyStrokeLimit(KeySequenceText.INFINITE);
        text.setKeySequence(zeroStrokeSequence);
        assertEquals(
                "Infinite limit changed zero stroke sequence.", zeroStrokeSequence, text.getKeySequence()); //$NON-NLS-1$
    }
