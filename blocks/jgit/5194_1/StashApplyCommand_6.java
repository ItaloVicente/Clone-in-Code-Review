			RevTree stashWorkingTree = stashCommit.getTree();
			RevTree stashIndexTree = revWalk.parseCommit(
					stashCommit.getParent(1)).getTree();
			RevTree stashHeadTree = revWalk.parseCommit(
					stashCommit.getParent(0)).getTree();

			CanonicalTreeParser stashWorkingIter = new CanonicalTreeParser();
			stashWorkingIter.reset(reader
			CanonicalTreeParser stashIndexIter = new CanonicalTreeParser();
			stashIndexIter.reset(reader
			CanonicalTreeParser stashHeadIter = new CanonicalTreeParser();
			stashHeadIter.reset(reader
			CanonicalTreeParser headIter = new CanonicalTreeParser();
			headIter.reset(reader

