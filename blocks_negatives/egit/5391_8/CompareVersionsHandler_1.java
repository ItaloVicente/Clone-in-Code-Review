				IPath location = new Path(fileInput.getAbsolutePath());
				try {
					CompareUtils.compare(location, repo, commit1.getName(),
							commit2.getName(), false, workBenchPage);
				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithRefAction_errorOnSynchronize, e,
							true);
				}
