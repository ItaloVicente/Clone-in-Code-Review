		if (lastFile == null) {
			lastFile = tmp = File
					.createTempFile("fsTickTmpFile"
		} else {
			if (!fs.exists(lastFile)) {
				throw new FileNotFoundException(lastFile.getPath());
			}
			tmp = File.createTempFile("fsTickTmpFile"
					lastFile.getParentFile());
		}
		long res = FS.getFsTimerResolution(tmp.toPath()).toMillis();
		long sleepTime = res / 10;
