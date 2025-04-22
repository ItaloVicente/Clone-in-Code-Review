				if (amending) {
					RevCommit previousCommit = new RevWalk(repo)
							.parseCommit(headId);
					RevCommit[] p = previousCommit.getParents();
					for (int i = 0; i < p.length; i++)
						parents.add(0
				} else {
					parents.add(0
				}
