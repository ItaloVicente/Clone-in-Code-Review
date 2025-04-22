    /** The shell on which the <code>KeySequenceText</code> is placed. */
    private Shell shell = null;

    /** The instance of <code>KeySequenceText</code> we should tinker with. */
    private KeySequenceText text = null;

    /**
     * Constructor for Bug42024Test.
     *
     * @param name
     *            The name of the test
     */
    public Bug42024Test(String name) {
        super(name);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
