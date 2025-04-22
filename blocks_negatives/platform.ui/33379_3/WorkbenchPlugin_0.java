        Object o = createExtension(
                IWorkbenchRegistryConstants.PL_PRESENTATION_FACTORIES,
                "factory", targetID); //$NON-NLS-1$
        if (o instanceof AbstractPresentationFactory) {
            return (AbstractPresentationFactory) o;
        }
        WorkbenchPlugin
        return null;
