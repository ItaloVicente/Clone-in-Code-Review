		ObjectChecker chk = new ObjectChecker()
			.setSafeForWindows(SystemReader.getInstance().isWindows())
			.setSafeForMacOS(SystemReader.getInstance().isMacOS());

		byte[] bytes = Constants.encode(path);
		int segmentStart = 0;
