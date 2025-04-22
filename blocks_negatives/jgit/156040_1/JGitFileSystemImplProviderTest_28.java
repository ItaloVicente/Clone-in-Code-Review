        try {
            provider.getFileSystem(newRepo);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid, missing host repository!");
        }
    }
