    /**
     * Tests that key pressed with key codes greater than 16 bits are correctly
     * converted into accelerator values.
     */
    public void testTruncatingCast() {
        /*
         * Make an event representing a key stroke with a key code greater than
         * 16 bits.
         */
        Event event = new Event();
        event.keyCode = SWT.ARROW_LEFT;
        event.character = 0x00;
        event.stateMask = 0x00;
