        try {
            return ((IContainer) o).members();
        } catch (CoreException e) {
            return NO_CHILDREN;
        }
    }
