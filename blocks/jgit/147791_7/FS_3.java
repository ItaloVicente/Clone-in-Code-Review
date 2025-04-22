		if (supportSymlinks == null) {
			detectSymlinkSupport();
		}
		return Boolean.TRUE.equals(supportSymlinks);
	}

	private void detectSymlinkSupport() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("tempsymlinktarget"
			File linkName = new File(tempFile.getParentFile()
			createSymLink(linkName
			supportSymlinks = Boolean.TRUE;
			linkName.delete();
		} catch (IOException | UnsupportedOperationException | SecurityException
				| InternalError e) {
			supportSymlinks = Boolean.FALSE;
		} finally {
			if (tempFile != null) {
				try {
					FileUtils.delete(tempFile);
				} catch (IOException e) {
				}
			}
		}
