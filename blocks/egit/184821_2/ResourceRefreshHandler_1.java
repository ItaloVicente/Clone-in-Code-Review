			IFile eclipseFile = eclipseWorkspace.getFileForLocation(filePath);
			if (eclipseFile != null
					&& !eclipseFile.getProject().isAccessible()) {
				eclipseFile = null;
				URI uri = URIUtil.toURI(filePath);
				IFile[] files = eclipseWorkspace.findFilesForLocationURI(uri);
				if (files.length > 1) {
					eclipseFile = Arrays.stream(files)
							.filter(f -> f.getProject().isAccessible())
							.findFirst().orElse(null);
				}
			}
			if (eclipseFile == null || !roots.keySet().stream()
