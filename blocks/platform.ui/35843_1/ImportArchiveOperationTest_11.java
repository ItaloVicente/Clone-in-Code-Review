            	for (IResource resource : resources) {
            		if (resource instanceof IContainer) {
						verifyFolder((IContainer)resource);
					} else {
						assertTrue("Root resource is not present or is not present as a file: " + rootResourceName,
            					resource instanceof IFile && rootResourceName.equals(resource.getName()));
					}
