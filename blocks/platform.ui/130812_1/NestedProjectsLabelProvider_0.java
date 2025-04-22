	private final class ErroModelSupplier implements Supplier<Map<IContainer, Integer>> {
		private Set<IResource> dirty = null;
		private Map<IContainer, Integer> cache = new HashMap<>();

		@Override
		public Map<IContainer, Integer> get() {
			Map<IContainer, Integer> severities = new HashMap<>();
			try {
				for (IMarker marker : WorkbenchNavigatorPlugin.getWorkspace().getRoot().findMarkers(IMarker.PROBLEM,
						true, IResource.DEPTH_INFINITE)) {
					IResource resource = marker.getResource();
					if (dirty == null || dirty.contains(resource)) {
						IContainer container = marker.getResource().getParent();
						if (marker.getResource() instanceof IContainer) {
							container = (IContainer) marker.getResource();
						}
						int severity = marker.getAttribute(IMarker.SEVERITY, -1);
						while (container != null) {
							if (!severities.containsKey(container) || severities.get(container).intValue() < severity) {
								severities.put(container, Integer.valueOf(severity));
								if (container.getType() == IResource.FOLDER) {
									container = container.getParent();
								} else if (container.getType() == IResource.PROJECT) {
									container = NestedProjectManager.getInstance()
											.getMostDirectOpenContainer((IProject) container);
								} else {
									container = null;
								}
							} else {
								container = null;
							}
						}
					}
					if (dirty != null) {
						dirty.remove(resource);
					}
				}
				cache.putAll(severities);
				if (dirty != null) {
					dirty.clear();
				} else {
					markDirty(Collections.emptySet());
				}
			} catch (CoreException e) {
				WorkbenchNavigatorPlugin.log(e.getMessage(),
						new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
				throw new RuntimeException(e);
			} catch (CancellationException e) {
				return Collections.emptyMap();
			}
			return cache;
		}

		public void markDirty(Set<IResource> dirty) {
			if (this.dirty == null) {
				this.dirty = new HashSet<>();
			}
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
	}

