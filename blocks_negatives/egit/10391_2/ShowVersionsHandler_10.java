					rev = CompareUtils.getFileRevision(gitPath, commit, map
							.getRepository(), null);
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							UIText.GitHistoryPage_errorLookingUpPath, gitPath,
							commit.getId()), e);
					errorOccurred = true;
				}
				if (rev != null) {
					if (compareMode) {
						ITypedElement right = CompareUtils
								.getFileRevisionTypedElement(gitPath, commit,
										map.getRepository());
						ITypedElement ancestor = CompareUtils.
								getFileRevisionTypedElementForCommonAncestor(
								gitPath, headCommit, commit, repo);
						final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
								SaveableCompareEditorInput
										.createFileElement(resource), right, ancestor,
								null);
						try {
							openInCompare(event, in);
						} catch (Exception e) {
							errorOccurred = true;
						}
