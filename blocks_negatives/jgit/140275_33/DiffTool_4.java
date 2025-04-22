					} catch (ToolException e) {
						outw.println(e.getResultStdout());
						outw.flush();
						errw.println(e.getMessage());
						throw die(MessageFormat.format(
								CLIText.get().diffToolDied, mergedFilePath), e);
