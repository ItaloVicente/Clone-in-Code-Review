        if (o instanceof IResource) {
            AdaptableResourceWrapper wrapper = new AdaptableResourceWrapper(
                    (IResource) o);
            return wrapper.getChildren();
        }
        return new Object[0];
    }

    /*
     * @see IWorkbenchAdapter#getImageDescriptor(Object)
     */
    @Override
