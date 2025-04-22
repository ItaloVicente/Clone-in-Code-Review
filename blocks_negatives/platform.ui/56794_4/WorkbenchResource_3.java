        if (o instanceof IResource) {
            return (IResource) o;
        }
        if (o instanceof IAdaptable) {
            return ((IAdaptable) o).getAdapter(IResource.class);
        }
        return null;
