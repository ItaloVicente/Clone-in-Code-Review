    private IWorkbench workbench;

    private Button sectionCheckbox;

    private Button subsectionCheckbox;

    private Button openFileCheckbox;

    private static int nameCounter = 1;

    /**
     * Creates the page for the readme creation wizard.
     *
     * @param workbench  the workbench on which the page should be created
     * @param selection  the current selection
     */
    public ReadmeCreationPage(IWorkbench workbench,
            IStructuredSelection selection) {
        super("sampleCreateReadmePage1", selection); //$NON-NLS-1$
        this.setDescription(MessageUtil
        this.workbench = workbench;
    }

    @Override
