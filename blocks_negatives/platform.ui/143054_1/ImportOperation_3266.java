        IContainer containerResource;
        try {
            containerResource = getDestinationContainerFor(folderObject);
        } catch (CoreException e) {
            errorTable.add(e.getStatus());
            return policy;
        }

        if (containerResource == null) {
