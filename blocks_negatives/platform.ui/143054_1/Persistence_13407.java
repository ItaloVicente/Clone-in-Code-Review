        String sourceId = sourceIdOverride != null ? sourceIdOverride : memento
                .getString(TAG_SOURCE_ID);
        return new CategoryDefinition(id, name, sourceId, description);
    }

    private Persistence() {
    }

    static public void log(IMemento memento, String elementName, String msg) {
    	if (memento instanceof ConfigurationElementMemento) {
    		ConfigurationElementMemento cMemento = (ConfigurationElementMemento) memento;
    		log(elementName, msg, cMemento.getContributorName(), cMemento.getExtensionID());
    	} else
    		log(elementName, msg, null, null);
    }

    static public void log(IConfigurationElement element, String elementName, String msg) {
    	String contributorName = element.getContributor().getName();
    	String extensionID = element.getDeclaringExtension().getUniqueIdentifier();
   		log(elementName, msg, contributorName, extensionID);
    }

    static public void log(String elementName, String msg, String contributorName, String extensionID) {
    	if (contributorName != null && extensionID != null)
    		msgInContext += NLS.bind(fullContextTemplate, contributorName, extensionID);
    	else if (contributorName != null)
    		msgInContext += NLS.bind(shortContextTemplate, contributorName);
        WorkbenchPlugin.log(msgInContext);
    }
