        	try {
        		targetResource.setResourceAttributes(((TarLeveledStructureProvider) provider).getResourceAttributes(fileObject));
        	} catch (CoreException e) {
        		errorTable.add(e.getStatus());
        	}
        }else if (fileObject instanceof ZipEntry) {
