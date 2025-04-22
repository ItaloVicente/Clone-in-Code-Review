				IPath repoPath = new Path(r.getWorkTree().getAbsolutePath());
				if (location != null && repoPath.isPrefixOf(location)) {
					if (repository == null
							|| repoPath.segmentCount() > largestSegmentCount) {
						repository = r;
						largestSegmentCount = repoPath.segmentCount();
