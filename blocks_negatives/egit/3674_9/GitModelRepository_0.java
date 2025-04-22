				GitModelCache gitModelCache = new GitModelCache(this,
						srcCommit, pathFilter);
				if (gitModelCache.getChildren().length > 0)
					result.add(gitModelCache);

				GitModelWorkingTree gitModelWorkingTree = getLocaWorkingTreeChanges();
				if (gitModelWorkingTree != null)
					result.add(gitModelWorkingTree);
