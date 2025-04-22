					} catch (ToolException e) {
						outw.println(e.getResultStdout());
						outw.flush();
						errw.println(e.getMessage());
						throw die("external diff died, stopping at " //$NON-NLS-1$
								+ mergedFilePath, e);
