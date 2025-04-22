				treeWalk.setFilter(new StashDiffFilter());

				treeWalk.addTree(stashHeadIter);
				treeWalk.addTree(stashIndexIter);
				treeWalk.addTree(stashWorkingIter);
				treeWalk.addTree(headIter);
				treeWalk.addTree(indexIter);
				treeWalk.addTree(workingIter);

				scanForConflicts(treeWalk);

				treeWalk.reset();
				stashWorkingIter.reset(reader
				stashIndexIter.reset(reader
				stashHeadIter.reset(reader
				treeWalk.addTree(stashHeadIter);
				treeWalk.addTree(stashIndexIter);
				treeWalk.addTree(stashWorkingIter);

				applyChanges(treeWalk
