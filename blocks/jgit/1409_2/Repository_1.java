			case ':': {
				RevTree tree;
				if (ref == null) {
					ObjectId id;
					try {
						if (i == 0)
							id = resolve(rw
						else
							id = resolve(rw
					} catch (RevisionSyntaxException badSyntax) {
						throw new RevisionSyntaxException(revstr);
					}
					if (id == null)
						return null;
					tree = rw.parseTree(id);
				} else {
					tree = rw.parseTree(ref);
				}

				if (i == rev.length - i)
					return tree.copy();

				TreeWalk tw = TreeWalk.forPath(rw.getObjectReader()
						new String(rev
				return tw != null ? tw.getObjectId(0) : null;
			}
