        } catch (IllegalArgumentException e) {
        	IDEWorkbenchPlugin.log("Could not parse version", new Status( //$NON-NLS-1$
        			IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
        			IStatus.ERROR,
        			e.getMessage() == null ? "" : e.getMessage(), //$NON-NLS-1$, 
        					e));
        	return null;
