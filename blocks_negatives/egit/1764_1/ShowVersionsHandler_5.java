					try {
						EgitUiEditorUtils.openEditor(getPart(event).getSite()
								.getPage(), rev, new NullProgressMonitor());
					} catch (CoreException e) {
						Activator.logError(UIText.GitHistoryPage_openFailed, e);
						errorOccured = true;
