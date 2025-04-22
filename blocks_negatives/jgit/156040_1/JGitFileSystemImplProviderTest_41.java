        try {
            provider.newDirectoryStream(path,
                                        null);
            failBecauseExceptionWasNotThrown(NotDirectoryException.class);
        } catch (NotDirectoryException ignored) {
        }
        try {
            provider.newDirectoryStream(crazyPath,
                                        null);
            failBecauseExceptionWasNotThrown(NotDirectoryException.class);
        } catch (NotDirectoryException ignored) {
        }
