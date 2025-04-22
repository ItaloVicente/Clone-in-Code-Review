        if (program == null) {
        	if (configurationElement == null) {
        		return Util.safeString(id);
        	}
        	return Util.safeString(configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID));

        }
        return Util.safeString(program.getName());
    }

    /**
     * Return the image descriptor describing this editor.
     *
     * @return the image descriptor
     */
    @Override
