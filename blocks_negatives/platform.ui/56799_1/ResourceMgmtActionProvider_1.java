			IProject project = null;

			if (next instanceof IProject) {
				project = (IProject) next;
			} else if (next instanceof IAdaptable) {
				project = ((IAdaptable) next).getAdapter(IProject.class);
			}
