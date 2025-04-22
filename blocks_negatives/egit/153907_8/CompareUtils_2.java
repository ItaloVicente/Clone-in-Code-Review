			FileElement remote = new FileElement(mergedFilePath,
					FileElement.Type.REMOTE, repository.getWorkTree(),
					rightRevision.getContents());

			/* ExecutionResult result = */
			diffTools.compare(local, remote, toolNameToUse, unset, false,
					unset, promptContinueHandler, tools -> {
						ToolsUtils.informUser("No tool configured.", //$NON-NLS-1$
					});
		} catch (ToolException e) {
			e.printStackTrace();
			ToolsUtils.informUserAboutError(
					"external diff died, stopping at " + mergedFilePath, //$NON-NLS-1$
					e.getResultStdout() + e.getMessage());
		} catch (CoreException e) {
			e.printStackTrace();
		}
