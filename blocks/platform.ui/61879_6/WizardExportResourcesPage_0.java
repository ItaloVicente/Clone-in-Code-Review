    private static class ResourceProvider extends WorkbenchContentProvider {
        private static final Object[] EMPTY = new Object[0];
        private int resourceType;
        private boolean showLinkedResources;

        public ResourceProvider(int resourceType, boolean showLinkedResources) {
            super();
            this.resourceType = resourceType;
            this.showLinkedResources = showLinkedResources;
        }

        @Override
		public Object[] getChildren(Object o) {
            if (o instanceof IContainer) {
                IContainer container = (IContainer) o;
                if (!showLinkedResources && container.isLinked(IResource.CHECK_ANCESTORS)) {
                    return EMPTY;
                }
                IResource[] members = null;
                try {
                    members = container.members();
                } catch (CoreException e) {
                    return EMPTY;
                }

                List<IResource> results = new ArrayList<>();
                for (int i = 0; i < members.length; i++) {
                    IResource resource = members[i];
                    if (!showLinkedResources && resource.isLinked()) {
                        continue;
                    }
                    if ((resource.getType() & resourceType) > 0) {
                        results.add(resource);
                    }
                }
                return results.toArray();
            }
            if (o instanceof ArrayList) {
                return ((List<?>) o).toArray();
            }
            return EMPTY;
        }
    }

