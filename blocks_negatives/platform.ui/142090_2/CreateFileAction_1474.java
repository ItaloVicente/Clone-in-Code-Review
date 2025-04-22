        while (resources.hasNext()) {
            IResource resource = resources.next();
            if (!resourceIsType(resource, IResource.PROJECT | IResource.FOLDER)
                    || !resource.isAccessible()) {
                return false;
            }
        }
        return true;
    }
