				} else {
					directoriesToVisit.addAll(Arrays.asList(currentDirectory.listFiles(new FileFilter() {
						@Override
						public boolean accept(File child) {
							return child.isDirectory();
						}
					})));
