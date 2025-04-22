		if (oldInput == null) {
			IResource resource = (IResource) newInput;
			resource.getWorkspace().addResourceChangeListener(this);
		}
		this.viewer = newViewer;
		this.input = (IResource) newInput;
	}

	@Override
