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
    public void worked(int work) {
        internalWorked(work);
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IProgressMonitorWithBlocking#clearBlocked()
     */
    public void clearBlocked() {
        blockedStatus = null;
        updateLabel();

    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IProgressMonitorWithBlocking#setBlocked(org.eclipse.core.runtime.IStatus)
     */
    public void setBlocked(IStatus reason) {
        blockedStatus = reason;
        updateLabel();

    }
    
   private void setCancelEnabled(boolean enabled) {
    	if (fStopButton != null && !fStopButton.isDisposed()) {
    		fStopButton.setEnabled(enabled);
    		if (enabled)
    			fToolBar.setFocus();
    	}
    }
