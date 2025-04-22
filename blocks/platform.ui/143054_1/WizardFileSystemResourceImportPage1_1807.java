				if (o instanceof MinimizedFileSystemElement) {
					MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
					return element.getFolders(
							fileSystemStructureProvider).getChildren(
							element);
				}
				return new Object[0];
			}

			@Override
