        if (element.getName().equals(TAG_HELP)) {
            readHelpElement(element);
            return true;
        }
        if (element.getName().equals(TAG_RESOLUTION_GENERATOR)) {
            readResolutionElement(element);
            return true;
        }
        if (element.getName().equals(TAG_ATTRIBUTE)) {
            readAttributeElement(element);
            return true;
        }
        return false;
    }

    /**
     * Processes a help configuration element.
     */
    private void readHelpElement(IConfigurationElement element) {
