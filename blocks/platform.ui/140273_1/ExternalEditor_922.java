		Program program = this.descriptor.getProgram();
		if (program == null) {
			openWithUserDefinedProgram();
		} else {
			String path = ""; //$NON-NLS-1$
			if (filePath != null) {
				path = filePath.toOSString();
				if (program.execute(path)) {
