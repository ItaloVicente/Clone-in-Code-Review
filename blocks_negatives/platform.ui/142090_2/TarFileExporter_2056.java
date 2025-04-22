        TarEntry newEntry = new TarEntry(destinationPath);
        if(container.getLocalTimeStamp() != IResource.NULL_STAMP) {
        	newEntry.setTime(container.getLocalTimeStamp() / 1000);
        }
        ResourceAttributes attributes = container.getResourceAttributes();
        if (attributes != null && attributes.isExecutable()) {
        	newEntry.setMode(newEntry.getMode() | 0111);
        }
        if (attributes != null && attributes.isReadOnly()) {
        	newEntry.setMode(newEntry.getMode() & ~0222);
        }
        newEntry.setFileType(TarEntry.DIRECTORY);
        outputStream.putNextEntry(newEntry);
    }
