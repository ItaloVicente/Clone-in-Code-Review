		if (dectedSupportSymlinks == null)
			dectedSymlinkSupport();
		return Boolean.TRUE.equals(dectedSupportSymlinks);
	}

	private void dectedSymlinkSupport() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("tempsymlinktarget"
			File linkName = new File(tempFile.getParentFile()
			FileUtil.createSymLink(linkName
			dectedSupportSymlinks = Boolean.TRUE;
			linkName.delete();
		} catch (IOException e) {
			dectedSupportSymlinks = Boolean.FALSE;
		} finally {
			if (tempFile != null)
				try {
					FileUtils.delete(tempFile);
				} catch (IOException e) {
				}
		}
