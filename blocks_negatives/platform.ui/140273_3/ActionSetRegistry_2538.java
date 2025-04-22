                    if (child.getName().equals(IWorkbenchRegistryConstants.TAG_PART)) {
                        String partId = child.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
                        if (partId != null) {
                            Object trackingObject = addAssociation(actionSetId, partId);
                            if (trackingObject != null) {
                                tracker.registerObject(extension,
                                        trackingObject,
                                        IExtensionTracker.REF_STRONG);

                            }

                        }
                    } else {
                                extension.getUniqueIdentifier());
                    }
                }
            }
        }

        mapPartToActionSets.clear();
    }

    /**
     * @param tracker
     * @param extension
     */
    private void addActionSets(IExtensionTracker tracker, IExtension extension) {
