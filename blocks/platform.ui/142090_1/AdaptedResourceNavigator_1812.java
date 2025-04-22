		Object obj = selection.getFirstElement();
		if (obj instanceof IFile && selection.size() == 1) {
			IFile file = (IFile) obj;
			IWorkbenchPage page = getSite().getPage();
			IEditorReference editorArray[] = page.getEditorReferences();
			for (IEditorReference element : editorArray) {
				IEditorPart editor = element.getEditor(true);
				IEditorInput input = editor.getEditorInput();
				if (input instanceof IFileEditorInput
						&& file.equals(((IFileEditorInput) input).getFile())) {
					page.bringToTop(editor);
					return;
				}
			}
		}
	}

	protected void makeActions() {
		actionGroup = new TestNavigatorActionGroup(this);
	}


	protected void restoreState(IMemento memento) {
	}

	@Override
