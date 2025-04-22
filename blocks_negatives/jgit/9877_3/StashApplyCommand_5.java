				treeWalk.reset();
				stashWorkingIter.reset(reader, stashWorkingTree);
				stashIndexIter.reset(reader, stashIndexTree);
				stashHeadIter.reset(reader, stashHeadTree);
				treeWalk.addTree(stashHeadIter);
				treeWalk.addTree(stashIndexIter);
				treeWalk.addTree(stashWorkingIter);
