	private Object[] getDirectoryChildren(RepositoryTreeNode parentNode,
			File parent) {
		Repository repo = parentNode.getRepository();
		List<RepositoryTreeNode<File>> children = new ArrayList<>();

		try {
			Files.walkFileTree(parent.toPath(),
					EnumSet.noneOf(FileVisitOption.class), 1,
					new SimpleFileVisitor<java.nio.file.Path>() {
						@Override
						public FileVisitResult visitFile(
								java.nio.file.Path file,
								BasicFileAttributes attrs) throws IOException {
							if (attrs.isDirectory()) {
								children.add(new FolderNode(parentNode, repo,
										file.toFile()));
							} else {
								children.add(new FileNode(parentNode, repo,
										file.toFile()));
							}
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(
								java.nio.file.Path file, IOException exc)
								throws IOException {
							return FileVisitResult.CONTINUE;
						}
					});
		} catch (IOException e) {
		}

		return children.toArray();
	}

