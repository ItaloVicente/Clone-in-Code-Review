					if (result.getStatus() == Status.NONDELETED) {
						retryDelete(result.getUndeletedList());
					}
					refreshAffectedProjects(progress);

					postExecute(progress.newChild(1));
