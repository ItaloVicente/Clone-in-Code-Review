		walk = beginWalk();
		assertIteration(F, ".gitattributes");
		assertIteration(D, "sub", attrs("global"));
		assertIteration(F, "sub/.gitattributes", attrs("init top_sub"));
		assertIteration(F, "sub/a.txt", attrs("init foo top top_sub"));
		assertIteration(D, "sub/sub", attrs("init subsub2 top_sub global"));
		assertIteration(F, "sub/sub/b.txt",
				attrs("init foo subsub top top_sub"));
		endWalk();
