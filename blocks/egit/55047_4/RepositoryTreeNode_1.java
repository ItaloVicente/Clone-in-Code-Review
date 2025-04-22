			return CommonUtils.STRING_ASCENDING_COMPARATOR.compare(
					getDirectoryContainingRepo((Repository) myObject)
							.getParentFile().getPath(),
					getDirectoryContainingRepo(
							(Repository) otherNode.getObject()).getParentFile()
									.getPath());
