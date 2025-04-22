					rev = CompareUtils.getFileRevision(gitPath, commit, map
							.getRepository(), null);
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							UIText.GitHistoryPage_errorLookingUpPath, gitPath,
							commit.getId()), e);
