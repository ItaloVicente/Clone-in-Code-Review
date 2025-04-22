					boolean addFile = false;
					if (trustExitCode) {
						System.out.println("trustExitCode: true"); //$NON-NLS-1$
						if (exitCode == 0) {
							addFile = true;
						}
					} else {
						System.out.println("trustExitCode: false"); //$NON-NLS-1$
						int response = ToolsUtils.askUserAboutToolExecution(
								"mergetool - trustExitCode: false", //$NON-NLS-1$
								"Merging file: " //$NON-NLS-1$
										+ mergedRelativeFilePath
										+ "\n\nWas the merge successful?"); //$NON-NLS-1$
						if (response == SWT.YES) {
							addFile = true;
						} else if (response == SWT.CANCEL) {
							return; // abort the merge process
