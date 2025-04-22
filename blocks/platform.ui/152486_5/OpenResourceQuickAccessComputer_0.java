			ResourcesPlugin.getWorkspace().getRoot().accept(resourceProxy -> {
				if (resourceProxy.isDerived() || !resourceProxy.isAccessible()) {
					return false;
				}

				if (resourceProxy.getType() == IResource.FILE) {
					String name = resourceProxy.getName();
					if (searchPattern.matches(name)) {
						IFile file = (IFile) resourceProxy.requestResource();
						res.add(new ResourceElement(labelProvider, file));
					}
