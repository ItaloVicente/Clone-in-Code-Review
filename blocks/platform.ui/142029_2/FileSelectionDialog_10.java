				if (o instanceof FileSystemElement) {
					return ((FileSystemElement) o).getFolders().getChildren(o);
				}
				return new Object[0];
			}
		};
	}

	private void initializeDialog() {
		if (getInitialElementSelections().isEmpty()) {
