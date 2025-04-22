	private static class FileLocationDetails {
		Path path;
		IFileStore fileStore;
		IFileInfo fileInfo;

		int line = -1;
		int column = -1;

		private static FileLocationDetails resolve(String path) {
			FileLocationDetails spec = checkLocation(path, -1, -1);
			if (spec != null) {
				return spec;
			}
			Pattern lPattern = Pattern.compile("^(?<path>.*?)[+:](?<line>\\d+)$"); //$NON-NLS-1$
			Pattern lcPattern = Pattern.compile("^(?<path>.*?)[+:](?<line>\\d+):(?<column>\\d+)$"); //$NON-NLS-1$
			Matcher m = lPattern.matcher(path);
			if (m.matches()) {
				try {
					spec = checkLocation(m.group("path"), Integer.parseInt(m.group("line")), -1); //$NON-NLS-1$//$NON-NLS-2$
					if (spec != null) {
						return spec;
					}
				} catch (NumberFormatException e) {
				}

			}
			m = lcPattern.matcher(path);
			if (m.matches()) {
				try {
					spec = checkLocation(m.group("path"), Integer.parseInt(m.group("line")), //$NON-NLS-1$//$NON-NLS-2$
							m.group("column") != null ? Integer.parseInt(m.group("column")) : -1); //$NON-NLS-1$ //$NON-NLS-2$
					if(spec != null) {
						return spec;
					}
				} catch (NumberFormatException e) {
				}
			}
			return null;
		}

		private static FileLocationDetails checkLocation(String path, int line, int column) {
			FileLocationDetails spec = new FileLocationDetails();
			spec.path = new Path(path);
			spec.fileStore = EFS.getLocalFileSystem().getStore(spec.path);
			spec.fileInfo = spec.fileStore.fetchInfo();
			spec.line = line;
			spec.column = column;
			return spec.fileInfo.exists() ? spec : null;
		}
	}

