		} else {
			for (Object element : elements) {
				final IResource resource = getResource(element);
				if (resource != null)
					try {
						resource.setSessionProperty(REFRESHED_KEY, null);
					} catch (CoreException e) {
					}
			}
