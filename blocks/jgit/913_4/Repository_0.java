								ref = rw.parseTree(ref);
							} else if (item.equals("commit")) {
								ref = rw.parseCommit(ref);
							} else if (item.equals("blob")) {
								ref = rw.peel(ref);
								if (!(ref instanceof RevBlob))
									throw new IncorrectObjectTypeException(ref
											Constants.TYPE_BLOB);
							} else if (item.equals("")) {
								ref = rw.peel(ref);
							} else
