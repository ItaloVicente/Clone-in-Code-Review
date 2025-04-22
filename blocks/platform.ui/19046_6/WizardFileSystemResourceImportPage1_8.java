        if (this.referenceExistingProjectRadio.getSelection()) {
        	this.projectDescription.setLocation(new Path(this.source.getAbsolutePath()));
        	CreateProjectOperation op = new CreateProjectOperation(this.projectDescription, this.projectDescription.getName());
        	IOperationHistory operationHistory = PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();
        	try {
        		IStatus status = operationHistory.execute(op, new NullProgressMonitor(), null);
        		if (status.isOK()) {
        			return true;
        		} else {
        			Platform.getLog(IDEWorkbenchPlugin.getDefault().getBundle()).log(status);
        			return false;
        		}
        	} catch (ExecutionException ex) {
        		Platform.getLog(IDEWorkbenchPlugin.getDefault().getBundle()).log(new Status(IStatus.ERROR, IDEWorkbenchPlugin.getDefault().getBundle().getSymbolicName(), ex.getMessage(), ex));
        		return false;
        	}
        } else if (this.resourcesRadio.getSelection()) {
	        Iterator resourcesEnum = getSelectedResources().iterator();
	        List fileSystemObjects = new ArrayList();
	        while (resourcesEnum.hasNext()) {
	            fileSystemObjects.add(((FileSystemElement) resourcesEnum.next())
	                    .getFileSystemObject());
	        }
	
	        if (fileSystemObjects.size() > 0) {
				return importResources(fileSystemObjects);
			}
	
	        MessageDialog.openInformation(getContainer().getShell(),
	                DataTransferMessages.DataTransfer_information,
	                DataTransferMessages.FileImport_noneSelected);
	
	        return false;
