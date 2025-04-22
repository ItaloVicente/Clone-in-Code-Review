			show(commits.get(0), commits.get(1));
		}
		return null;
	}

	public static void show(IRepositoryCommit tip, IRepositoryCommit base) {
		if (tip == null) {
			return;
		}
		DiffEditorInput input = new DiffEditorInput(tip, base);
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		IEditorReference[] editors = page.findEditors(input,
				DiffEditor.EDITOR_ID,
				IWorkbenchPage.MATCH_ID + IWorkbenchPage.MATCH_INPUT);
		if (editors != null && editors.length > 0) {
			IEditorPart existing = editors[0].getEditor(false);
			if (existing != null) {
				page.activate(existing);
				return;
			}
		}
		DiffEditor.DiffJob job = DiffEditor.getDiffer(tip, base);
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent evt) {
				if (!evt.getResult().isOK()) {
					return;
