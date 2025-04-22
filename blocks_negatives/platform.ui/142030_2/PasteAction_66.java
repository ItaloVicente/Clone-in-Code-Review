        if (resourceData != null && resourceData.length > 0) {
            if (resourceData[0].getType() == IResource.PROJECT) {
                for (IResource resource : resourceData) {
                    CopyProjectOperation operation = new CopyProjectOperation(
                            this.shell);
                    operation.copyProject((IProject) resource);
                }
            } else {
                IContainer container = getContainer();
