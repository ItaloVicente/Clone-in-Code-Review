                IResource resource = ((IMarker) o).getResource();
                v.add(resource);
            } else if (o instanceof IAdaptable) {
                IResource resource = ((IAdaptable) o).getAdapter(IResource.class);
                if (resource != null) {
                    v.add(resource);
                }
