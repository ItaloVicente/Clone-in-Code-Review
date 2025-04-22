    /**
     * Tests that a <code>StackOverflowError</code> does not occur when
     * trying to set the key sequence in a key sequence entry widget.
     *
     * @throws ParseException
     *             If "CTRL+" is not recognized as a key sequence.
     */
    public void testStackOverflow() throws ParseException {
        Display display = Display.getCurrent();
        Shell shell = new Shell(display);
        shell.setLayout(new RowLayout());
        Text text = new Text(shell, SWT.BORDER);
        KeySequenceText keySequenceText = new KeySequenceText(text);
