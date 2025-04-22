    /**
     * Create a new instance of the receiver
     *
     * @param parent
     *            the parent Composite
     * @param style
     *            the SWT style
     * @param pageId
     *            the page id
     * @param message
     *            the message to use as text. If this message has {0} in
     *            its value it will be bound with the displayed name of
     *            the preference page. This message must be well formed
     *            html if you wish to link to another page.
     * @param pageContainer -
     *            The container another page will be opened in.
     * @param pageData -
     *            The data to apply to the page.
     */
    public PreferenceLinkArea(Composite parent, int style, final String pageId,
            String message, final IWorkbenchPreferenceContainer pageContainer,
            final Object pageData) {
        pageLink = new Link(parent, style);
