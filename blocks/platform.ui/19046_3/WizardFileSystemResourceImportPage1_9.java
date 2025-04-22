    
    protected boolean determinePageCompletion() {
    	setErrorMessage(null);
    	if (this.newProjectRadio.getSelection()) {
    		return true;
    	} else if (this.existingProjectRadio.getSelection()) {
    		InputStream projectDescrptionStream = null;
    		try {
	    		projectDescrptionStream = new FileInputStream(new File(this.source, IProjectDescription.DESCRIPTION_FILE_NAME));
	    		this.projectDescription = ResourcesPlugin.getWorkspace().loadProjectDescription(projectDescrptionStream);
	    		if (ResourcesPlugin.getWorkspace().getRoot().getProject(this.projectDescription.getName()).exists()) {
	    			setErrorMessage(NLS.bind(IDEWorkbenchMessages.WizardImportPage_projectAlreadyExists, this.projectDescription.getName()));
	    			return false;
	    		}
    			return true;
    		} catch (Exception ex) {
    			setErrorMessage(ex.getLocalizedMessage());
    			return false;
    		} finally {
    			if (projectDescrptionStream != null) {
    				try {
    					projectDescrptionStream.close();
    				} catch (IOException ioException) {
    				}
    			}
    		}
    		
    	} else if (this.resourcesRadio.getSelection()) {
	        if (noOpenProjects()) {
	            setErrorMessage(IDEWorkbenchMessages.WizardImportPage_noOpenProjects);
	            return false;
	        }
        }
        return super.determinePageCompletion();
    }
    
    private boolean noOpenProjects() {
        IProject[] projects = IDEWorkbenchPlugin.getPluginWorkspace().getRoot()
                .getProjects();
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].isOpen()) {
				return false;
			}
        }
        return true;
    }

	public boolean useNestedWizard() {
		return this.newProjectRadio.getSelection();
	}
