		for (final Path root : fs.getRootDirectories()) {
			if (root.toAbsolutePath().toUri().toString().contains("upstream")) {
				assertThat(provider.newDirectoryStream(root
			} else if (root.toAbsolutePath().toUri().toString().contains("origin")) {
				assertThat(provider.newDirectoryStream(root
			} else {
				assertThat(provider.newDirectoryStream(root
			}
		}
