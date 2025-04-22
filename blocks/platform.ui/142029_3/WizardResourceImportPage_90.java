	protected ResourceTreeAndListGroup selectionGroup;

	private static final String EMPTY_FOLDER_MESSAGE = IDEWorkbenchMessages.WizardImportPage_specifyFolder;

	private static final String EMPTY_PROJECT_MESSAGE = IDEWorkbenchMessages.WizardImportPage_specifyProject;

	private static final String INACCESSABLE_FOLDER_MESSAGE = IDEWorkbenchMessages.WizardImportPage_folderMustExist;

	protected WizardResourceImportPage(String name, IStructuredSelection selection) {
		super(name);

		currentResourceSelection = null;
		if (selection.size() == 1) {
			Object firstElement = selection.getFirstElement();
