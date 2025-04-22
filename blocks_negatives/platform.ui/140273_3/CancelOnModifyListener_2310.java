    /**
     * Constructs a new instance of <code>CancelOnModifyListener</code>
     *
     * @param listener
     *            The listener which should be removed in the event of a
     *            modification event arriving; should not be <code>null</code>.
     */
    CancelOnModifyListener(Listener listener) {
        chainedListener = listener;
    }
