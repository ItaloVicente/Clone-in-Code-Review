        IProject project = (IProject) o;
        if (project.isOpen()) {
            try {
                return project.members();
            } catch (CoreException e) {
            }
        }
        return NO_CHILDREN;
    }
