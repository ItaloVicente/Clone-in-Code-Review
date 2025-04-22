                IResource resource = null;
                if (o instanceof IResource) {
                    resource = (IResource) o;
                } else {
                    if (o instanceof IAdaptable) {
                        resource = ((IAdaptable) o).getAdapter(IResource.class);
                    }
                }
