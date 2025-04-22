			if (!link.isPresent()) {
				return;
			}
			Path p = link.get();
			if (!Files.exists(p)) {
				return;
			}
			try {
				Files.delete(p);
			} catch (IOException e) {
				LOG.error(MessageFormat
						.format(JGitText.get().closeLockTokenFailed
