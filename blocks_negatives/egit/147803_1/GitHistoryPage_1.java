			if (showHead)
				showHead(repo);
			if (showRef)
				showRef(ref, repo);
			if (showTag)
				showTag(ref, repo);
			if (selection != null)
				graph.selectCommitStored(selection);

