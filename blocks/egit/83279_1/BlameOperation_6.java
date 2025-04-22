			if (fileRevision != null) {
				editorPart = EgitUiEditorUtils.openEditor(page, fileRevision,
						null);
			} else {
				editorPart = IDE.openEditor(page, (IFile) storage,
						OpenStrategy.activateOnOpen());
			}
		} catch (CoreException e) {
