		walk = beginWalk();
		assertIteration(F, ".gitattributes");
		assertIteration(D, "sub");
		assertIteration(F, "sub/.gitattributes");
		assertIteration(F, "sub/a.txt", attrs("foo bar3"));
		endWalk();
