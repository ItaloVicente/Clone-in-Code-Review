			try (org.eclipse.jgit.transport.UploadPack up = new org.eclipse.jgit.transport.UploadPack(
					db)) {
				if (0 <= timeout) {
					up.setTimeout(timeout);
				}
				up.upload(ins
