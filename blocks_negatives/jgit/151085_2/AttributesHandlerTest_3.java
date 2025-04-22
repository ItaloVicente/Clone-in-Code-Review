		walk = beginWalk();
		assertIteration(F, ".gitattributes");
		assertIteration(D, "sub", attrs("global"));
		assertIteration(F, "sub/.gitattributes", attrs("init top_sub"));
		assertIteration(F, "sub/a.txt", attrs("init foo top top_sub"));
		endWalk();
