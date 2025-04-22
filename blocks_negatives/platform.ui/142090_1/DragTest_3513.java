        manageWindows(false);

        if (window == null) {
            window = (WorkbenchWindow) fWorkbench.openWorkbenchWindow(
            	"org.eclipse.ui.tests.dnd.dragdrop", getPageInput());

            page = (WorkbenchPage) window.getActivePage();

            file1 = FileUtil.createFile("DragTest1.txt", project); //$NON-NLS-1$
            file2 = FileUtil.createFile("DragTest2.txt", project); //$NON-NLS-1$
            file3 = FileUtil.createFile("DragTest3.txt", project); //$NON-NLS-1$

            IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
            apiStore.setValue(
                    IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS,
                    false);
        }

        page.resetPerspective();
        page.closeAllEditors(false);

        page.showView("org.eclipse.ui.views.ContentOutline");
        page.hideView(page.findView("org.eclipse.ui.internal.introview"));
        editor1 = page.openEditor(new FileEditorInput(file1),
                MockEditorPart.ID1);
        editor2 = page.openEditor(new FileEditorInput(file2),
                MockEditorPart.ID2);
        editor3 = page.openEditor(new FileEditorInput(file3),
                MockEditorPart.ID2);

        window.getShell().setActive();
        DragOperations
                .drag(editor2, new EditorDropTarget(new ExistingWindowProvider(window), 0, SWT.CENTER), false);
        DragOperations
                .drag(editor3, new EditorAreaDropTarget(new ExistingWindowProvider(window), SWT.RIGHT), false);
    }

    /**
     * This method is useful in order to 'freeze' the test environment after a particular test in order to
     * manipulate the environment to figure out what's going on. It essentially opens a new shell and enters
     * a modal loop on it, preventing the tests from continuing until the 'stall' shell is closed. Note that
     * using a dialog would prevent us from manipulating the shell that the drag and drop tests are being performed in
     */
    public void stallTest() {
    	String[] testNames = {
    	};

    	boolean testNameMatches = false;
    	for (String testName : testNames) {
    		if (testName.equals(this.getName())) {
    			testNameMatches = true;
    			break;
    		}
    	}

    	if (testNames.length == 0 || testNameMatches) {
	    	Display display = Display.getCurrent();
	    	Shell loopShell = new Shell(display, SWT.SHELL_TRIM);
	    	loopShell.setBounds(0,0,200,100);
	    	loopShell.setText("Test Stall Shell");
	    	loopShell.setVisible(true);

	    	while (loopShell != null && !loopShell.isDisposed()) {
	    		if (!display.readAndDispatch()) {
