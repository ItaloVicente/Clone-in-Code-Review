			Repository r = walk.getRepository();
			ObjectId newHead1 = r.resolve(Constants.FETCH_HEAD);
			ObjectId newHead2;
			try (SubmoduleWalk walk2 = SubmoduleWalk.forIndex(r)) {
				assertTrue(walk2.next());
				newHead2 = walk2.getRepository().resolve(Constants.FETCH_HEAD);
