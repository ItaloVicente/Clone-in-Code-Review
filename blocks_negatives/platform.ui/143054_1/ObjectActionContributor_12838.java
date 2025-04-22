        return ((ObjectContribution) currentContribution)
                .isApplicableTo(object);
    }

    /**
     * Reads the configuration element and all the children.
     * This creates an action descriptor for every action in the extension.
     */
    private void readConfigElement() {
        currentContribution = createContribution();
        readElementChildren(config);
        configRead = true;
    }

    @Override
