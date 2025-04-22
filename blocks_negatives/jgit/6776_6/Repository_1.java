					ObjectId id;
					try {
						if (i == 0)
							id = resolve(rw, Constants.HEAD);
						else
							id = resolve(rw, new String(revChars, 0, i));
					} catch (RevisionSyntaxException badSyntax) {
						throw new RevisionSyntaxException(revstr);
					}
					if (id == null)
						return null;
					tree = rw.parseTree(id);
				} else {
					tree = rw.parseTree(rev);
