
		getSite().setSelectionProvider(revObjectSelectionProvider);
	}

	private void openInCompare(CompareEditorInput input) {
		IWorkbenchPage workBenchPage = getSite().getPage();
		IEditorPart editor = findReusableCompareEditor(input, workBenchPage);
		if (editor != null) {
			IEditorInput otherInput = editor.getEditorInput();
			if (otherInput.equals(input)) {
				if (OpenStrategy.activateOnOpen())
					workBenchPage.activate(editor);
				else
					workBenchPage.bringToTop(editor);
			} else {
				CompareUI.reuseCompareEditor(input, (IReusableEditor) editor);
				if (OpenStrategy.activateOnOpen())
					workBenchPage.activate(editor);
				else
					workBenchPage.bringToTop(editor);
			}
		} else {
			CompareUI.openCompareEditor(input);
		}
	}

	/**
	 * Returns an editor that can be re-used. An open compare editor that
	 * has un-saved changes cannot be re-used.
	 * @param input the input being opened
	 * @param page
	 * @return an EditorPart or <code>null</code> if none can be found
	 */
	private IEditorPart findReusableCompareEditor(CompareEditorInput input, IWorkbenchPage page) {
		IEditorReference[] editorRefs = page.getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			IEditorPart part = editorRefs[i].getEditor(false);
			if (part != null
					&& (part.getEditorInput() instanceof GitCompareFileRevisionEditorInput)
					&& part instanceof IReusableEditor
					&& part.getEditorInput().equals(input)) {
				return part;
			}
		}
		if (isReuseOpenEditor()) {
			for (int i = 0; i < editorRefs.length; i++) {
				IEditorPart part = editorRefs[i].getEditor(false);
				if (part != null
						&& (part.getEditorInput() instanceof SaveableCompareEditorInput)
						&& part instanceof IReusableEditor && !part.isDirty()) {
					return part;
				}
			}
		}
		return null;
	}

	private static boolean isReuseOpenEditor() {
		boolean defaultReuse = new DefaultScope().getNode(TEAM_UI_PLUGIN).getBoolean(REUSE_COMPARE_EDITOR_PREFID, false);
		return new InstanceScope().getNode(TEAM_UI_PLUGIN).getBoolean(REUSE_COMPARE_EDITOR_PREFID, defaultReuse);
