        while (resources.hasNext()) {
            IProject currentResource = (IProject) resources.next();
            if (currentResource.isOpen()) {
                return true;
            }
        }
        return false;
