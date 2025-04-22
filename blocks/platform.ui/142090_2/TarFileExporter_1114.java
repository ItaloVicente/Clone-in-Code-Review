		TarEntry newEntry = new TarEntry(destinationPath);
		if(resource.getLocalTimeStamp() != IResource.NULL_STAMP) {
			newEntry.setTime(resource.getLocalTimeStamp() / 1000);
		}
		ResourceAttributes attributes = resource.getResourceAttributes();
		if (attributes != null && attributes.isExecutable()) {
			newEntry.setMode(newEntry.getMode() | 0111);
		}
		if (attributes != null && attributes.isReadOnly()) {
			newEntry.setMode(newEntry.getMode() & ~0222);
		}
		write(newEntry, resource);
	}
