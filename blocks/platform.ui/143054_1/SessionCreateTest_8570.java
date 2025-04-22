	private IWorkbenchWindow[] oldWindows;

	public static String TEST_PROJ = "sessionTest";

	public static String TEST_FILE_1 = "one.mock1";

	public static String TEST_FILE_2 = "two.mock1";

	public static String TEST_FILE_3 = "three.mock1";

	public SessionCreateTest(String arg) {
		super(arg);
	}

	public void testSessionCreation() throws Throwable {
		IWorkbenchWindow window;
		IWorkbenchPage page;

		saveOriginalWindows();

		window = fWorkbench.openWorkbenchWindow(EmptyPerspective.PERSP_ID, getPageInput());

		window = fWorkbench.openWorkbenchWindow(EmptyPerspective.PERSP_ID, getPageInput());
		page = window.openPage(SessionPerspective.ID, getPageInput());

		window = fWorkbench.openWorkbenchWindow(SessionPerspective.ID, getPageInput());
		page = window.openPage(SessionPerspective.ID, getPageInput());

		IProject proj = FileUtil.createProject(TEST_PROJ);
		IFile file = FileUtil.createFile(TEST_FILE_1, proj);
		page.openEditor(new FileEditorInput(file), MockEditorPart.ID1);
		file = FileUtil.createFile(TEST_FILE_2, proj);
		page.openEditor(new FileEditorInput(file), MockEditorPart.ID1);
		file = FileUtil.createFile(TEST_FILE_3, proj);
		page.openEditor(new FileEditorInput(file), MockEditorPart.ID1);

		closeOriginalWindows();
	}

	private void saveOriginalWindows() {
		oldWindows = fWorkbench.getWorkbenchWindows();
	}

	private void closeOriginalWindows() {
		for (IWorkbenchWindow oldWindow : oldWindows) {
			oldWindow.close();
		}
	}
