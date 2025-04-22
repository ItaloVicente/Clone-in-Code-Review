		walk = beginWalk();
		assertIteration(F, ".gitattributes");
		assertIteration(D, "sub");
		assertIteration(F, "sub/a.txt");
		assertIteration(F, "sub/ndw", attrs("bar"));
		assertIteration(D, "sub/new", attrs("bar"));
		assertIteration(F, "sub/new/foo.txt");
		endWalk();
