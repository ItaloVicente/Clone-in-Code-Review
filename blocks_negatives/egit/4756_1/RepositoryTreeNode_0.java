			return ((Repository) myObject).getDirectory().getParentFile()
					.getParentFile().getPath().compareTo(
							(((Repository) otherNode.getObject())
									.getDirectory().getParentFile()
									.getParentFile().getPath()));

