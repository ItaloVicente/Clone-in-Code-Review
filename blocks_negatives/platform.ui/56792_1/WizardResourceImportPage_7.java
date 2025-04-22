            if (firstElement instanceof IAdaptable) {
                Object resource = ((IAdaptable) firstElement)
                        .getAdapter(IResource.class);
                if (resource != null) {
					currentResourceSelection = (IResource) resource;
				}
            }
