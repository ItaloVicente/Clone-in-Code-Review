					if (exitCode == 0) {
						if (!trustExitCode) {
							int response = ToolsUtils.askUserAboutToolExecution(
									"mergetool", //$NON-NLS-1$
											+ mergedCompareFilePath
							if (response == SWT.YES) {
								/*
								 * TODO: implement add
								AddCommand addCommand = new Git(repo).add();
								boolean fileAdded = false;
								for (String path : notTracked)
									if (commitFileList.contains(path)) {
										addCommand.addFilepattern(path);
										fileAdded = true;
									}
								if (fileAdded)
									try {
										addCommand.call();
									} catch (Exception e) {
										throw new CoreException(Activator
												.error(e.getMessage(), e));
									}
								*/
							} else if (response == SWT.CANCEL) {
								return;
							}
