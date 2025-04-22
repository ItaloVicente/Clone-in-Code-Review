			return getDirectoryContainingRepo((Repository) myObject)
					.getParentFile()
					.getPath()
					.compareTo(
							getDirectoryContainingRepo((Repository) otherNode.getObject())
									.getParentFile().getPath());
