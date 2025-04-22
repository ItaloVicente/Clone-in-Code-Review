			}
			case Constants.OBJ_TREE: {
				treeWalk.getEntryObjectId(idBuffer);
				final RevTree t = lookupTree(idBuffer);
				if ((t.flags & UNINTERESTING) == 0) {
					t.flags |= UNINTERESTING;
					treeWalk = treeWalk.createSubtreeIterator0(reader, t);
					continue;
				}
