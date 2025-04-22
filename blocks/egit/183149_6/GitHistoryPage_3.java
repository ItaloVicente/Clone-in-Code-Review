			if (showAllFilter == ShowFilter.SHOWALLPROJECT
					|| showAllFilter == ShowFilter.SHOWALLREPO) {
				return new ArrayList<>(0);
			}
			paths = new ArrayList<>(inFiles.length);
			if (db.isBare()) {
				for (File file : inFiles) {
					if (!file.isAbsolute()) {
						IPath filePath = Path
								.fromPortableString(file.getPath());
						paths.add(new FilterPath(filePath.toString(), false));
					}
				}
				return paths;
			}
