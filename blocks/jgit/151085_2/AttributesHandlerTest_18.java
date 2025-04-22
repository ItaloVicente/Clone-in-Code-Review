		try (TreeWalk walk = beginWalk()) {
			assertIteration(walk
			assertIteration(walk
			assertIteration(walk
					attrs("init top_sub"));
			assertIteration(walk
					attrs("init foo top top_sub"));
			assertIteration(walk
					attrs("init subsub2 top_sub global"));
			assertIteration(walk
					attrs("init foo subsub top top_sub"));
			assertFalse("Not all files tested"
		}
