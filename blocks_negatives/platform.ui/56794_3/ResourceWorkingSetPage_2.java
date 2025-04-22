            IResource resource = null;

            if (elements[i] instanceof IResource) {
				resource = (IResource) elements[i];
			} else {
				resource = elements[i].getAdapter(IResource.class);
			}
