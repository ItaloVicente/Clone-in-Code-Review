								ref = rw.parseTree(ref);
							} else if (item.equals("commit")) {
								ref = rw.parseCommit(ref);
							} else if (item.equals("blob")) {
								ref = rw.parseAny(ref);
								ref = peelTag(rw
								if (!(ref instanceof RevBlob))
									throw new IncorrectObjectTypeException(ref
											Constants.TYPE_BLOB);
							} else if (item.equals("")) {
								ref = rw.parseAny(ref);
								ref = peelTag(rw
							} else
