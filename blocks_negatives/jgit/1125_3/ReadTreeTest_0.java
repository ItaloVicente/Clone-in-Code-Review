		GitIndex theIndex = new GitIndex(db);
		theIndex.add(trash, writeTrashFile("DF", "DF"));
		Tree treeDF = db.mapTree(theIndex.writeTree());

		recursiveDelete(new File(trash, "DF"));
		theIndex = new GitIndex(db);
		theIndex.add(trash, writeTrashFile("DF/DF", "DF/DF"));
		Tree treeDFDF = db.mapTree(theIndex.writeTree());

		theIndex = new GitIndex(db);
		recursiveDelete(new File(trash, "DF"));

		theIndex.add(trash, writeTrashFile("DF", "DF"));
		theIndex.write();
