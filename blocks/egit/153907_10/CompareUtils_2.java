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
					BooleanTriState trustExitCode = BooleanTriState.UNSET;
					BooleanTriState prompt = BooleanTriState.FALSE;
					DiffTools diffToolMgr = new DiffTools(repository);
					diffToolMgr.compare(local, remote, toolName, prompt, gui,
							trustExitCode, promptContinueHandler, tools -> {
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
