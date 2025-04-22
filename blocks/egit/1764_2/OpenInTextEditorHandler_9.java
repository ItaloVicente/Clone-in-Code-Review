				if (rev != null) {
					try {
						EgitUiEditorUtils.openTextEditor(getPart(event)
								.getSite().getPage(), rev, null);
					} catch (CoreException e) {
						Activator.logError(e.getMessage(), e);
						errorOccured = true;
					}
				} else {
					ids.add(commit.getId());
				}
