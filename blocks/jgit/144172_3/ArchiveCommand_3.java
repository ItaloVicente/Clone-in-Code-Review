				RevObject o = rw.peel(rw.parseAny(tree));
				final RevTree t;
				if (o instanceof RevCommit) {
					t = ((RevCommit) o).getTree();
				} else if (!(o instanceof RevTree)) {
					throw new IncorrectObjectTypeException(tree.toObjectId()
							Constants.TYPE_TREE);
				} else {
					t = (RevTree) o;
				}
				walk.reset(t);
