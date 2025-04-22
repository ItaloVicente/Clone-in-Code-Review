		etcDir = new File(trash
		FileUtils.mkdir(etcDir);
		gitDir = new File(trash
		FileUtils.mkdir(gitDir);
		otherDir = new File(trash
		FileUtils.mkdir(otherDir);

		fsMock = new FS_Mock();
		userConfig = OpenSshConfig.get(fsMock);
		systemConfig = OpenSshConfig.get(fsMock
