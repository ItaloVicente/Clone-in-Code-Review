			} catch (CancellationException e) {
				return Collections.emptyMap();
			}
			return cache;
		}

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
