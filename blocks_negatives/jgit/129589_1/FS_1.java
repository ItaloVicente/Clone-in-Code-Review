			if (link.isPresent()) {
				try {
					Files.delete(link.get());
				} catch (IOException e) {
					LOG.error(MessageFormat.format(JGitText.get().closeLockTokenFailed,
							this), e);
				}
