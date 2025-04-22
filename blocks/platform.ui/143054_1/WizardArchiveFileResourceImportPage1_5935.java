				if (o instanceof MinimizedFileSystemElement) {
					MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
					AdaptableList l = element.getFiles(structureProvider);
					return l.getChildren(element);
				}
				return new Object[0];
			}
		};
	}

	@Override
