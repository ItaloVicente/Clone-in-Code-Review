					} catch (ToolException e) {
						outw.println(e.getResultStdout());
						outw.flush();
						errw.println(e.getMessage());
						errw.flush();
						throw die(MessageFormat.format(
								CLIText.get().diffToolDied, mergedFilePath, e),
								e);
