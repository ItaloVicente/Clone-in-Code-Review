					File file = entry.getKey().append(fileName).toFile();
					isDirectory = file.isDirectory();
					if (isDirectory) {
						isDirectory = !Files
								.isSymbolicLink(FileUtils.toPath(file));
					}
