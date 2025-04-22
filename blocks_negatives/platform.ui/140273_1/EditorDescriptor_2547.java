        IEditorActionBarContributor contributor = null;
        try {
            contributor = (IEditorActionBarContributor) WorkbenchPlugin
                    .createExtension(configurationElement,
                            IWorkbenchRegistryConstants.ATT_CONTRIBUTOR_CLASS);
        } catch (CoreException e) {
                    id, e.getStatus());
        }
        return contributor;
    }

    /**
     * Return the editor class name.
     *
     * @return the class name
     */
    public String getClassName() {
    	if (configurationElement == null) {
    		return className;
    	}
    	return RegistryReader.getClassValue(configurationElement,
                IWorkbenchRegistryConstants.ATT_CLASS);
    }

    /**
     * Return the configuration element used to define this editor, or <code>null</code>.
     *
     * @return the element or null
     */
    public IConfigurationElement getConfigurationElement() {
        return configurationElement;
    }

    /**
     * Create an editor part based on this descriptor.
     *
     * @return the editor part
     * @throws CoreException thrown if there is an issue creating the editor
     */
    public IEditorPart createEditor() throws CoreException {
        Object extension = WorkbenchPlugin.createExtension(getConfigurationElement(), IWorkbenchRegistryConstants.ATT_CLASS);
        return ((InterceptContributions)Tweaklets.get(InterceptContributions.KEY)).tweakEditor(extension);
    }

    /**
     * Return the file name of the command to execute for this editor.
     *
     * @return the file name to execute
     */
    public String getFileName() {
        if (program == null) {
        	if (configurationElement == null) {
        		return fileName;
        	}
        	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_COMMAND);
    	}
        return program.getName();
    }

    /**
     * Return the id for this editor.
     *
     * @return the id
     */
    @Override
