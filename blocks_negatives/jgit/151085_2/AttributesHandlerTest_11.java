		walk = beginWalk();
		assertIteration(F, ".gitattributes");
		assertIteration(D, "sub");
		assertIteration(F, "sub/a.txt");
		assertIteration(D, "sub/new");
		assertIteration(F, "sub/new/foo.txt", attrs("bar"));
		assertIteration(D, "sub/new/lower", attrs("bar"));
		assertIteration(F, "sub/new/lower/foo.txt");
		endWalk();
