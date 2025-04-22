            }
        }
        if (resourceData != null) {
            if (isLinked(resourceData)
                && targetResource.getType() != IResource.PROJECT
                && targetResource.getType() != IResource.FOLDER) {
