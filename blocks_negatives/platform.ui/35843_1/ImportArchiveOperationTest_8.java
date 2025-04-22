            	for (int i = 0; i < resources.length; i++){
            		if (resources[i] instanceof IContainer)
            			verifyFolder((IContainer)resources[i]);
            		else
            			assertTrue("Root resource is not present or is not present as a file: " + rootResourceName, 
            					resources[i] instanceof IFile && rootResourceName.equals(resources[i].getName()));
