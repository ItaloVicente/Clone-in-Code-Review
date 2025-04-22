		walk = beginWalk();
		assertIteration(F, ".gitattributes");
		assertIteration(D, "foo");
		assertIteration(F, "foo/s.txt", attrs("bar"));
		assertIteration(F, "foo/sext", attrs("bar"));
		assertIteration(D, "sub");
		assertIteration(F, "sub/a.txt");
		endWalk();
