	private CompletableFuture<Map<IResource, Integer>> refreshSeverities(Set<IResource> dirty) {
		if (dirty != null) {
			supplier.markDirty(dirty);
		}
		return CompletableFuture.supplyAsync(supplier);
	}

	private final class ProblemsModelSupplier implements Supplier<Map<IResource, Integer>> {
		private Set<IResource> dirty = null;
		private Map<IResource, Integer> cache = new HashMap<>();

		@Override
		public Map<IResource, Integer> get() {
			Set<IResource> currentDirty = null;
			if (dirty != null) {
				currentDirty = new HashSet<>();
				currentDirty.addAll(this.dirty);
				this.dirty.removeAll(currentDirty);
				removeFromCache(currentDirty);
			}

			try {
				for (IMarker marker : WorkbenchNavigatorPlugin.getWorkspace().getRoot().findMarkers(IMarker.PROBLEM,
						true, IResource.DEPTH_INFINITE)) {
					IResource resource = marker.getResource();
					if (currentDirty == null || currentDirty.contains(resource)) {
						int severity = marker.getAttribute(IMarker.SEVERITY, 0);
						if (severity >= 0) {
							propagateSeverityToCache(resource, severity);
						}
					}
				}
				markDirty(Collections.emptySet());
			} catch (CoreException e) {
				WorkbenchNavigatorPlugin.log(e.getMessage(),
						new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
				throw new RuntimeException(e);
			} catch (CancellationException e) {
				return Collections.emptyMap();
			}
			return cache;
		}

		/**
		 * Removes element from the cache and then fix the parent hierarchy for removed
		 * elements (remove from cache if no problem remain under node, or re-compute
		 * and propagate new highest severity)
		 *
		 * @param toRemove
		 */
		private void removeFromCache(Set<IResource> toRemove) {
			Set<IContainer> dirtyLeafContainers = new HashSet<>();
			for (IResource resource : toRemove) {
				final IContainer initialContainer = resource instanceof IContainer ? (IContainer) resource
						: resource.getParent();
				dirtyLeafContainers
						.removeIf(leafContainer -> leafContainer.getLocation()
								.isPrefixOf(initialContainer.getLocation()));
				if (dirtyLeafContainers.stream()
						.noneMatch(leafContainer -> initialContainer.getLocation()
								.isPrefixOf(leafContainer.getLocation()))) {
					dirtyLeafContainers.add(initialContainer);
				}
				if (resource.getType() == IResource.FILE) {
					cache.remove(resource);
				}
				IContainer container = initialContainer;
				while (container != null && cache.containsKey(container)) {
					cache.remove(container);
					container = getParentInView(container);
				}
			}
			dirtyLeafContainers.forEach(leafContainer -> {
				IContainer container = leafContainer;
				while (container != null) {
					int severity = getMaxChildrenSeverityInCache(container);
					if (severity >= 0) {
						propagateSeverityToCache(container, severity);
					}
					container = getParentInView(container);
				}
			});
		}

		private int getMaxChildrenSeverityInCache(IContainer container) {
			if (!container.isAccessible()) {
				return -1;
			}
			Set<IResource> children = new HashSet<>();
			try {
				children.addAll(Arrays.asList(container.members()));
			} catch (CoreException ex) {
				WorkbenchNavigatorPlugin.log("Cannot access members", //$NON-NLS-1$
						WorkbenchNavigatorPlugin.createErrorStatus(ex.getMessage(), ex));
			}
			children.addAll(Arrays.asList(NestedProjectManager.getInstance().getDirectChildrenProjects(container)));
			int[] severity = new int[] { -1 };
			children.forEach(child -> {
				if (cache.containsKey(child) && cache.get(child).intValue() > severity[0]) {
					severity[0] = cache.get(child).intValue();
				}
			});
			return severity[0];
		}

		private void propagateSeverityToCache(IResource resource, int severity) {
			while (resource != null) {
				if (!cache.containsKey(resource) || cache.get(resource).intValue() < severity) {
					cache.put(resource, Integer.valueOf(severity));
					resource = getParentInView(resource);
				} else {
					resource = null;
				}
			}
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
