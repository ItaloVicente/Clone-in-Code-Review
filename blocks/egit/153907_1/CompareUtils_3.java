					if (DiffPreferencePage
							.getDiffToolMode() == DiffToolMode.EXTERNAL_FOR_TYPE
							&& !toolName.isPresent()) {
						openCompareToolInternal(workbenchPage, input);
						return Status.OK_STATUS;
					}

					PromptContinueHandler promptContinueHandler = new FileNamePromptContinueHandler(
							mergedFilePath);

					@SuppressWarnings("restriction")
					boolean swapSides = CompareUIPlugin.getDefault()
							.getPreferenceStore()
							.getBoolean(ComparePreferencePage.SWAPPED);
					Type typeLocal;
					Type typeRemote;
					if (swapSides) {
						typeLocal = FileElement.Type.REMOTE;
						typeRemote = FileElement.Type.LOCAL;
					} else {
						typeLocal = FileElement.Type.LOCAL;
						typeRemote = FileElement.Type.REMOTE;
					}

					FileElement local = null;
					if (leftRevision != null) {
						local = new FileElement(mergedFilePath, typeLocal,
								repository.getWorkTree(),
								leftRevision.getContents());
					} else {
						local = new FileElement(mergedFilePath, typeLocal,
								repository.getWorkTree());
					}
					FileElement remote = null;
					if (rightRevision != null) {
						remote = new FileElement(mergedFilePath, typeRemote,
								repository.getWorkTree(),
								rightRevision.getContents());
					} else {
						remote = new FileElement(mergedFilePath, typeRemote,
								repository.getWorkTree());
					}

					boolean gui = false;
					diffToolMgr.compare(local, remote, toolName, unset, gui,
							unset, promptContinueHandler, tools -> {
								ToolsUtils.informUser(
										UIText.CompareUtils_NoDiffToolsDefined,
										UIText.CompareUtils_NoDiffToolSpecified);
							});
				} catch (ToolException e) {
					ToolsUtils.informUserAboutError(
							UIText.CompareUtils_ExternalDiffToolDied
									+ mergedFilePath,
							e.getMessage());
				} catch (CoreException e) {
					ToolsUtils.informUserAboutError(
							UIText.CompareUtils_DiffToolExecutionFailed
									+ mergedFilePath,
							e.getMessage());
				}
				return Status.OK_STATUS;
