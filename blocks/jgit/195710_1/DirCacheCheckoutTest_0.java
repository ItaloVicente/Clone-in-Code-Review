
		doit(mkmap(longFileName1
		writeTrashFile(longFileName1
		checkout();
		assertNoConflicts();
		assertUpdated(longFileName1);

		doit(mkmap(longFileName2
		writeTrashFile(longFileName2
		checkout();
		assertNoConflicts();
		assertUpdated(longFileName2);

		doit(mkmap(longFileName3
		writeTrashFile(longFileName3
		checkout();
		assertNoConflicts();
		assertUpdated(longFileName3);

		doit(mkmap(longFileName4
		writeTrashFile(longFileName4
