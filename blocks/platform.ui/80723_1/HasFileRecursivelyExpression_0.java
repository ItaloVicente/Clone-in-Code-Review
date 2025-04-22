				}
				File[] files = currentDirectory.listFiles(new FileFilter() {
					@Override
					public boolean accept(File child) {
						return child.isDirectory();
					}
				});
				if (files != null) {
					directoriesToVisit.addAll(Arrays.asList(files));
