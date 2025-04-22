				GitModelCache gitModelCache = new GitModelCache(this, srcCommit);
				if (gitModelCache.getChildren().length > 0)
					result.add(gitModelCache);

				GitModelWorkingTree gitModelWorkingTree = new GitModelWorkingTree(
						this, srcCommit);
				if (gitModelWorkingTree.getChildren().length > 0)
					result.add(gitModelWorkingTree);
