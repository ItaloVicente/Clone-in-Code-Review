						rev = CompareUtils.getFileRevision(commitPath, commit,
								map.getRepository(), null);
					} catch (IOException e) {
						Activator.logError(NLS.bind(
								UIText.GitHistoryPage_errorLookingUpPath,
								commitPath, commit.getId()), e);
