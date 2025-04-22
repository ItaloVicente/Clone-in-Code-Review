        if (resources.size() > 0) {
            IResource resource = resources.get(0);
            return resource.getParent();
        }
        return null;
    }
