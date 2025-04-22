			try {
				targetResource.setResourceAttributes(((TarLeveledStructureProvider) provider).getResourceAttributes(fileObject));
				timeStamp = ((TarEntry)fileObject).getTime()*1000; // TarEntry time is in secs. Convert to msecs
			} catch (CoreException e) {
				errorTable.add(e.getStatus());
			}
		}else if (fileObject instanceof ZipEntry) {
