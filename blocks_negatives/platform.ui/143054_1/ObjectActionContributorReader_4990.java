        String tagName = element.getName();
        if (tagName.equals(IWorkbenchRegistryConstants.TAG_OBJECT_CONTRIBUTION)) {
            processObjectContribution(element);
            return true;
        }
        if (tagName.equals(IWorkbenchRegistryConstants.TAG_VIEWER_CONTRIBUTION)) {
            return true;
        }
