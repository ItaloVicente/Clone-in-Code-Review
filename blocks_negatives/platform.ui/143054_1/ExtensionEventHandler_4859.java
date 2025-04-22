            ArrayList appearList = new ArrayList(5);
            ArrayList revokeList = new ArrayList(5);
            String id = null;
            int numPerspectives = 0;
            int numActionSetPartAssoc = 0;

            for (IExtensionDelta extensionDelta : delta) {
                id = extensionDelta.getExtensionPoint().getSimpleIdentifier();
                if (extensionDelta.getKind() == IExtensionDelta.ADDED) {
                    if (id.equals(IWorkbenchRegistryConstants.PL_ACTION_SETS)) {
