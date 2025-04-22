						try (SubmoduleWalk walk = SubmoduleWalk
								.forIndex(node.getRepository())) {
							while (walk.next()) {
								Repository subRepo = getCached(cache,
										walk.getRepository());
								if (subRepo != null) {
									subRepos.add(subRepo);
								}
