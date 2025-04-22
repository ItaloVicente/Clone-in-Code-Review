        try {
            provider.newFileSystem(newRepo,
                                   EMPTY_ENV);
            failBecauseExceptionWasNotThrown(FileSystemAlreadyExistsException.class);
        } catch (FileSystemAlreadyExistsException ignored) {
        }
