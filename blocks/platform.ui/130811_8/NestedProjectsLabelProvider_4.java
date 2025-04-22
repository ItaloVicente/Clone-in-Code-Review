		}

		private IContainer getParentInView(IResource resource) {
			if (resource.getType() == IResource.PROJECT) {
				return NestedProjectManager.getInstance().getMostDirectOpenContainer((IProject) resource);
			}
			return resource.getParent();
		}

		public void markDirty(Set<IResource> dirty) {
			synchronized (this) {
				if (this.dirty == null) {
					this.dirty = new HashSet<>();
				}
			}
			this.dirty.addAll(dirty);
		}
