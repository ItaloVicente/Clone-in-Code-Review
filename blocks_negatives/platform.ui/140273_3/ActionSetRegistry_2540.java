        String extensionPointUniqueIdentifier = extension.getExtensionPointUniqueIdentifier();
        if (extensionPointUniqueIdentifier.equals(getActionSetExtensionPoint().getUniqueIdentifier())) {
            removeActionSets(objects);
        }
        else if (extensionPointUniqueIdentifier.equals(getActionSetPartAssociationExtensionPoint().getUniqueIdentifier())){
            removeActionSetPartAssociations(objects);
        }
    }

    /**
     * @param objects
     */
    private void removeActionSetPartAssociations(Object[] objects) {
        for (Object object : objects) {
            if (object instanceof ActionSetPartAssociation) {
                ActionSetPartAssociation association = (ActionSetPartAssociation) object;
                String actionSetId = association.actionSetId;
                ArrayList actionSets = (ArrayList) mapPartToActionSetIds.get(association.partId);
                if (actionSets == null) {
