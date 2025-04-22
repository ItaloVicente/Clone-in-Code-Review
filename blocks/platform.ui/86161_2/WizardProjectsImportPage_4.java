		if (initialPath != null) {
			this.initialPath = initialPath;
		} else {
			if (currentSelection != null) {
				Object firstElement = currentSelection.getFirstElement();
				if (firstElement instanceof File) {
					this.initialPath = ((File) firstElement).getAbsolutePath();
				} else if (firstElement instanceof IResource) {
					this.initialPath = ((IResource) firstElement).getLocation().toFile().getAbsolutePath();
				} else if (firstElement instanceof String && new File((String) firstElement).exists()) {
					this.initialPath = new File((String) firstElement).getAbsolutePath();
				}
			}
		}
