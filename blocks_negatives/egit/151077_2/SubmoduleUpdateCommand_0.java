						SubmoduleWalk walk = SubmoduleWalk
								.forIndex(node.getRepository());
						while (walk.next()) {
							Repository subRepo = walk.getRepository();
							if (subRepo != null) {
								subRepos.add(subRepo);
