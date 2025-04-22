        IGotoMarker {

    /** The text editor used in page 0. */
    private TextEditor editor;

    /** The index of the page containing the text editor */
    private int editorIndex = 0;

    /** The font chosen in page 1. */
    private Font font;

    /** The text widget used in page 2. */
    private StyledText text;

    /**
     * Creates a multi-page editor example.
     */
    public MultiPageEditorExample() {
        super();
    }

    /**
     * Creates page 0 of the multi-page editor,
     * which contains a text editor.
     */
    void createPage0() {
        try {
            editor = new TextEditor();
            editorIndex = addPage(editor, getEditorInput());
            setPageText(editorIndex, MessageUtil.getString("Source")); //$NON-NLS-1$
        } catch (PartInitException e) {
            ErrorDialog
                    .openError(
                            getSite().getShell(),
                            MessageUtil.getString("ErrorCreatingNestedEditor"), null, e.getStatus()); //$NON-NLS-1$
        }
    }

    /**
     * Creates page 1 of the multi-page editor,
     * which allows you to change the font used in page 2.
     */
    void createPage1() {

        Composite composite = new Composite(getContainer(), SWT.NONE);
        GridLayout layout = new GridLayout();
        composite.setLayout(layout);
        layout.numColumns = 2;

        Button fontButton = new Button(composite, SWT.NONE);
        GridData gd = new GridData(GridData.BEGINNING);
        gd.horizontalSpan = 2;
        fontButton.setLayoutData(gd);

        fontButton.addSelectionListener(new SelectionAdapter() {
            @Override
