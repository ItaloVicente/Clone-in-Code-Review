			RevTree stashWorkingTree = stashCommit.getTree();
			RevTree stashIndexTree = revWalk.parseCommit(
					stashCommit.getParent(1)).getTree();
			RevTree stashHeadTree = revWalk.parseCommit(
					stashCommit.getParent(0)).getTree();

			CanonicalTreeParser stashWorkingIter = new CanonicalTreeParser();
			stashWorkingIter.reset(reader, stashWorkingTree);
			CanonicalTreeParser stashIndexIter = new CanonicalTreeParser();
			stashIndexIter.reset(reader, stashIndexTree);
			CanonicalTreeParser stashHeadIter = new CanonicalTreeParser();
			stashHeadIter.reset(reader, stashHeadTree);
			CanonicalTreeParser headIter = new CanonicalTreeParser();
			headIter.reset(reader, headTree);
