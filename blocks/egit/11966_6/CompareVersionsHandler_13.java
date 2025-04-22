				IResource[] resources = new IResource[] { (IFile) input, };
				try {
					CompareUtils.compare(resources, repo, commit1.getName(),
							commit2.getName(), false, workBenchPage);
				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithRefAction_errorOnSynchronize, e,
							true);
				}
