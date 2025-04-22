	private IProject _project;

	private static final String PROJECT_NAME = "DummyProject";

	public UIPreferences(String name) {
		super(name);
	}

	private Shell getShell() {
		return DialogCheck.getShell();
	}

	private IProject getDummyProject() {
		try {
			IProject projects[] = ResourcesPlugin.getWorkspace().getRoot()
					.getProjects();
			for (IProject project : projects) {
				if (project.getName().equals(PROJECT_NAME)) {
					project.delete(true, null);
					break;
				}
			}
			_project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					PROJECT_NAME);
			_project.create(null);
		} catch (CoreException e) {
			System.out.println(e);
		}
		return _project;
	}

	private PreferenceDialog getPreferenceDialog(String id) {
		PreferenceDialogWrapper dialog = null;
		PreferenceManager manager = WorkbenchPlugin.getDefault()
				.getPreferenceManager();
		if (manager != null) {
			dialog = new PreferenceDialogWrapper(getShell(), manager);
			dialog.create();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
					IWorkbenchHelpContextIds.PREFERENCE_DIALOG);

			for (Object element : manager.getElements(
					PreferenceManager.PRE_ORDER)) {
				IPreferenceNode node = (IPreferenceNode) element;
				if (node.getId().equals(id)) {
					dialog.showPage(node);
					break;
				}
			}
		}
		return dialog;
	}

	private PropertyDialog getPropertyDialog(String id) {
		PropertyDialogWrapper dialog = null;

		PropertyPageManager manager = new PropertyPageManager();
		String title = "";
		String name = "";

		IProject element = getDummyProject();
		if (element == null) {
			return null;
		}
		PropertyPageContributorManager.getManager()
				.contribute(manager, element);
