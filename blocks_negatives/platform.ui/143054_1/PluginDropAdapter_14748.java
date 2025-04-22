        IExtensionRegistry registry = Platform.getExtensionRegistry();
        String adapterName = data.getExtensionId();
        IExtensionPoint xpt = registry.getExtensionPoint(PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_DROP_ACTIONS);
        IExtension[] extensions = xpt.getExtensions();
        for (IExtension extension : extensions) {
            IConfigurationElement[] configs = extension.getConfigurationElements();
            if (configs != null && configs.length > 0) {
                for (IConfigurationElement config : configs) {
                    if (id != null && id.equals(adapterName)) {
                        return (IDropActionDelegate) WorkbenchPlugin
                                .createExtension(config, ATT_CLASS);
                    }
                }
            }
        }
        return null;
    }
