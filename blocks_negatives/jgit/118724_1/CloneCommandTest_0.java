		Repository clonedSub1 = walk.getRepository();
		assertNotNull(clonedSub1);
		assertEquals(
				new File(git2.getRepository().getWorkTree(), walk.getPath()),
				clonedSub1.getWorkTree());
		assertEquals(new File(new File(git2.getRepository().getDirectory(),
				"modules"), walk.getPath()),
				clonedSub1.getDirectory());
		status = new SubmoduleStatusCommand(clonedSub1);
		statuses = status.call();
		clonedSub1.close();
