        String extensionPointUniqueIdentifier = extension.getExtensionPointUniqueIdentifier();
        if (extensionPointUniqueIdentifier.equals(getActionSetExtensionPoint().getUniqueIdentifier())) {
            addActionSets(tracker, extension);
        }
        else if (extensionPointUniqueIdentifier.equals(getActionSetPartAssociationExtensionPoint().getUniqueIdentifier())){
            addActionSetPartAssociations(tracker, extension);
        }
    }

    /**
     * @param tracker
     * @param extension
     */
    private void addActionSetPartAssociations(IExtensionTracker tracker, IExtension extension) {
