		File packName = new File(new File(
				((FileObjectDatabase) repo.getObjectDatabase()).getDirectory(),
				"pack"), idA.name() + ".pack");
		File idxName = new File(new File(
				((FileObjectDatabase) repo.getObjectDatabase()).getDirectory(),
				"pack"), idA.name() + ".idx");
