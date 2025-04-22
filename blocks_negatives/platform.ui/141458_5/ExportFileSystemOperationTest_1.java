		for (IResource member : members) {
			resources.add(member);
		}
        FileSystemExportOperation operation =
        	new FileSystemExportOperation(
        			null, resources, localDirectory, this);
        openTestWindow().run(true, true, operation);
