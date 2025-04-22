			} else {
				try {
					EgitUiEditorUtils.openEditor(getPart(event).getSite()
							.getPage(), revision, new NullProgressMonitor());
				} catch (CoreException e) {
					Activator.logError(UIText.GitHistoryPage_openFailed, e);
					errorOccurred = true;
