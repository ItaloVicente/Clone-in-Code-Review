				return escapeMetaCharacters(JFaceResources.format(
    					"Set_SubTask", new Object[] { fTaskName, fSubTaskName }));//$NON-NLS-1$
   			return escapeMetaCharacters(fTaskName);
   			
    	} else if (hasSubtask) {
    		return escapeMetaCharacters(fSubTaskName);
    	
    	} else {
    	}
    }

    /**
     * Implements <code>IProgressMonitor.worked</code>.
     * @see IProgressMonitor#worked(int)
     */
    @Override
