        Program program = this.descriptor.getProgram();
        if (program == null) {
            openWithUserDefinedProgram();
        } else {
            if (filePath != null) {
                path = filePath.toOSString();
                if (program.execute(path)) {
