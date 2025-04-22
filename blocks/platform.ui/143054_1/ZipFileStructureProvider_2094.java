		try {
			if (invalidEntries.contains(((ZipEntry) element).getName())) {
				throw new IOException("Cannot get content of Entry as it is outside of the target dir: " //$NON-NLS-1$
						+ ((ZipEntry) element).getName());
			}
			return zipFile.getInputStream((ZipEntry) element);
		} catch (IOException e) {
			IDEWorkbenchPlugin.log(e.getLocalizedMessage(), e);
			return null;
		}
	}

	@Override
