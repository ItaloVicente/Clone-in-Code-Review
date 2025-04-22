
			DfsCommitGraph oldCommitGraph = commitGraphs.remove(dsc);
			if (oldCommitGraph != null) {
				newCommitGraphs.add(oldCommitGraph);
			} else if (dsc.hasFileExt(PackExt.COMMIT_GRAPH)) {
				newCommitGraphs.add(new DfsCommitGraph(cache
				foundNew = true;
			}
