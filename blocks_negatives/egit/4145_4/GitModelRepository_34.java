				GitModelWorkingTree gitWorkingTree = getLocaWorkingTreeChanges();
				int gitWorkingTreeLen = gitWorkingTree != null ? gitWorkingTree
						.getChildren().length : 0;

				if (gitCacheLen > 0 || gitWorkingTreeLen > 0) {
					result.add(gitCache);
					result.add(gitWorkingTree);
				}
			}
