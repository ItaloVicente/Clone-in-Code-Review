			IRepositoryCommit tip = commits.get(0);
			if (tip != null) {
				DiffEditorInput input = new DiffEditorInput(tip,
						commits.get(1));
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
						return null;
					}
