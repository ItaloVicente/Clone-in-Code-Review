		walk = beginWalk();
		assertIteration(F, ".gitattributes");
		assertIteration(D, "sub");
		assertIteration(F, "sub/a.txt");
		assertIteration(D, "sub/new", attrs("bar"));
		assertIteration(F, "sub/new/foo.txt");
		endWalk();
