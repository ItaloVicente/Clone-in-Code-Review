					FileElement local = null;
					if (leftRevision != null) {
						local = new FileElement(changedFilePath,
								typeLocal,
								repository.getWorkTree(),
								leftRevision.getContents());
					} else {
						local = new FileElement(changedFilePath,
								typeLocal,
								repository.getWorkTree());
					}
					FileElement remote = null;
					if (rightRevision != null) {
						remote = new FileElement(changedFilePath,
								typeRemote,
								repository.getWorkTree(),
								rightRevision.getContents());
					} else {
						remote = new FileElement(changedFilePath,
								typeRemote,
								repository.getWorkTree());
					}

					boolean gui = false;
					Optional<Boolean> unset = Optional.empty();
					DiffToolManager diffToolMgr = new DiffToolManager(
							repository);
					diffToolMgr.compare(local, remote, toolName, unset, gui,
							unset, promptContinueHandler, tools -> {
								ToolsUtils.informUser(
										UIText.CompareUtils_NoDiffToolsDefined,
										UIText.CompareUtils_NoDiffToolSpecified);
							});
				} catch (ToolException e) {
					ToolsUtils.informUserAboutError(
							UIText.CompareUtils_ExternalDiffToolDied
									+ changedFilePath,
							e.getMessage());
				} catch (CoreException e) {
					ToolsUtils.informUserAboutError(
							UIText.CompareUtils_DiffToolExecutionFailed
									+ changedFilePath,
							e.getMessage());
				}
				return Status.OK_STATUS;
