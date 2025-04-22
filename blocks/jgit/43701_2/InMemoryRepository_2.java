			if (oldRef == null)
				return refs.putIfAbsent(name

			synchronized (refs) {
				Ref cur = refs.get(name);
				Ref toCompare = cur;
				if (toCompare != null) {
					if (toCompare.isSymbolic()) {
						Ref leaf = toCompare.getLeaf();
						if (leaf.getObjectId() == null) {
							leaf = refs.get(leaf.getName());
							if (leaf.isSymbolic())
								throw new IllegalArgumentException();
							toCompare = new SymbolicRef(
									name
									new ObjectIdRef.Unpeeled(
											Storage.NEW
											leaf.getName()
											leaf.getObjectId()));
						} else
							toCompare = toCompare.getLeaf();
					}
					if (eq(toCompare
						return refs.replace(name
				}
			}

			if (oldRef.getStorage() == Storage.NEW)
