			if (rev != null)
				EgitUiEditorUtils.openEditor(page, rev,
						new NullProgressMonitor());
			else {
				String message = NLS.bind(
						UIText.CommitFileDiffViewer_notContainedInCommit, d.path,
						d.commit.getId().getName());
				Activator.showError(message, null);
			}
