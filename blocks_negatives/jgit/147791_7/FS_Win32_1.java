	/** {@inheritDoc} */
	@Override
	public boolean supportsSymlinks() {
		if (supportSymlinks == null) {
			detectSymlinkSupport();
		}
		return Boolean.TRUE.equals(supportSymlinks);
	}

	private void detectSymlinkSupport() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("tempsymlinktarget", ""); //$NON-NLS-1$ //$NON-NLS-2$
			File linkName = new File(tempFile.getParentFile(), "tempsymlink"); //$NON-NLS-1$
			createSymLink(linkName, tempFile.getPath());
			supportSymlinks = Boolean.TRUE;
			linkName.delete();
		} catch (IOException | UnsupportedOperationException
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
	}

