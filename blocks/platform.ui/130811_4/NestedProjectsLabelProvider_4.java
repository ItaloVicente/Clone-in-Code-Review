			this.dirty.addAll(dirty);
			for (IResource resource : dirty) {
				IContainer container = resource instanceof IContainer ? (IContainer) resource : resource.getParent();
				while (container != null) {
					cache.remove(container);
					dirty.add(container);
					if (container.getType() == IResource.FOLDER) {
						container = container.getParent();
					} else if (container.getType() == IResource.PROJECT) {
						container = NestedProjectManager.getInstance().getMostDirectOpenContainer((IProject) container);
					} else {
						container = null;
					}
				}
			}
		}
