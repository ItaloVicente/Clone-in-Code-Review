				ITypedElement right;
				if (conflicting)
					right = CompareUtils.getFileRevisionTypedElement(gitPath,
							rightCommit, repository);
				else
					right = CompareUtils.getFileRevisionTypedElement(gitPath,
							headCommit, repository);
