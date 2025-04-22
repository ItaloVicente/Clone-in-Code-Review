		try {
			provider.getPath(uri);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (final IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid
		}
	}
