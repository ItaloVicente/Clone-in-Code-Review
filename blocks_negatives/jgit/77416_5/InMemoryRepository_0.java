				Ref toCompare = cur;
				if (toCompare != null) {
					if (toCompare.isSymbolic()) {
						Ref leaf = toCompare.getLeaf();
						if (leaf.getObjectId() == null) {
							leaf = refs.get(leaf.getName());
							if (leaf.isSymbolic())
								throw new IllegalArgumentException();
							toCompare = new SymbolicRef(
									name,
									new ObjectIdRef.Unpeeled(
											Storage.NEW,
											leaf.getName(),
											leaf.getObjectId()));
						} else
							toCompare = toCompare.getLeaf();
					}
					if (eq(toCompare, oldRef))
