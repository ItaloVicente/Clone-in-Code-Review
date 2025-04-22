        String tag = element.getName();

        if (tag.equals(IWorkbenchRegistryConstants.TAG_OBJECT_CONTRIBUTION)) {
            return true;
        }

        if (tag.equals(targetContributionTag)) {
            if (targetID != null) {
                String id = getTargetID(element);
                if (id == null || !id.equals(targetID)) {
