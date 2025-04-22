				if (headCommit != null) {
					RevCommit[] headParents = headCommit.getParents();
					for (int i = 0; i < 2; i++) {
						RevCommit possibleParent = rw.next();
						for (RevCommit parent : headParents)
							if (parent.equals(possibleParent))
								result.add(possibleParent);
					}
				}
