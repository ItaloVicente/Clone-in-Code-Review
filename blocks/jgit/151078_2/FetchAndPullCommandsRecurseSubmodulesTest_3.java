			try (Repository r = walk.getRepository()) {
				newHead1 = r.resolve(Constants.FETCH_HEAD);
				try (SubmoduleWalk walk2 = SubmoduleWalk.forIndex(r)) {
					assertTrue(walk2.next());
					try (Repository r2 = walk2.getRepository()) {
						newHead2 = r2.resolve(Constants.FETCH_HEAD);
					}
				}
