				List<File> list =
					Files.find(directory.toPath(), deep,
							(java.nio.file.Path filePath,
								BasicFileAttributes fileAttr) -> check(filePath, fileAttr)).map(path -> path.toFile())
								.collect(Collectors.toList());
			files.addAll(list);
