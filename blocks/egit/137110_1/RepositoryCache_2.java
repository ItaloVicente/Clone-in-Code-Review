					continue;
				}
				if (repo.isBare()) {
					continue;
				}
				IPath repoPath = new Path(repo.getWorkTree().getAbsolutePath());
				if (location != null && repoPath.isPrefixOf(location)) {
					if (repository == null
							|| repoPath.segmentCount() > largestSegmentCount) {
						if (!repo.getDirectory().exists()) {
							i.remove();
							toRemove.add(entry.getKey());
							continue;
						}
						repository = repo;
						largestSegmentCount = repoPath.segmentCount();
					}
