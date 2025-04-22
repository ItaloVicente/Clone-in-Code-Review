        values.add(desc);

        return true;

    }

    /**
     * Return the DecoratorDefinition defined by element or <code>null</code>
     * if it cannot be determined.
     * @param element
     * @return DecoratorDefinition
     */
    DecoratorDefinition getDecoratorDefinition(IConfigurationElement element){

        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (ids.contains(id)) {
            logDuplicateId(element);
            return null;
        }
        ids.add(id);

        boolean noClass = element.getAttribute(DecoratorDefinition.ATT_CLASS) == null;

        if (Boolean.valueOf(element.getAttribute(IWorkbenchRegistryConstants.ATT_LIGHTWEIGHT)).booleanValue() || noClass) {

            String iconPath = element.getAttribute(LightweightDecoratorDefinition.ATT_ICON);

            if (noClass && iconPath == null) {
                logMissingElement(element, LightweightDecoratorDefinition.ATT_ICON);
                return null;
            }

            return new LightweightDecoratorDefinition(id, element);
        }
        return new FullDecoratorDefinition(id, element);

    }

    /**
     * Read the decorator extensions within a registry and set
     * up the registry values.
     */
    Collection readRegistry(IExtensionRegistry in) {
        values.clear();
        ids.clear();
        readRegistry(in, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_DECORATORS);
        return values;
    }

    /**
     * Return the values.
     *
     * @return the values
     */
    public Collection getValues() {
        return values;
    }

    /**
     * Logs a registry error when the configuration element is unknown.
     */
    protected void logDuplicateId(IConfigurationElement element) {
        logError(element, "Duplicate id found: " + element.getAttribute(IWorkbenchRegistryConstants.ATT_ID));//$NON-NLS-1$
    }
