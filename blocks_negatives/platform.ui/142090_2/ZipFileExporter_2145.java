            throws IOException {
        if (!resolveLinks && container.isLinked(IResource.DEPTH_INFINITE)) {
            return;
        }
        ZipEntry newEntry = new ZipEntry(destinationPath);
        outputStream.putNextEntry(newEntry);
    }
