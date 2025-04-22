				if (base instanceof EmptyTypedElement
						&& next instanceof EmptyTypedElement) {
					RevCommit[] ids = new RevCommit[] { commit1, commit2 };
					StringBuilder commitList = new StringBuilder();
					for (RevCommit commit : ids) {
						commitList.append("\n"); //$NON-NLS-1$
						commitList.append(commit.getShortMessage());
						commitList.append(' ');
						commitList.append('[');
						commitList.append(commit.name());
						commitList.append(']');
					}
					String message = NLS.bind(
							UIText.GitHistoryPage_notContainedInCommits,
							gitPath, commitList.toString());
					IStatus errorStatus = new Status(IStatus.ERROR, Activator
							.getPluginId(), message);
					EgitUiEditorUtils
							.openErrorEditor(
									getPart(event).getSite().getPage(),
									errorStatus,
									UIText.CompareVersionsHandler_ErrorOpeningCompareEditorTitle,
									null);
					return null;
				}
