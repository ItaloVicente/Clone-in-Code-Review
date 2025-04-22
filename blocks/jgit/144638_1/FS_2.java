				try {
					Files.delete(probe);
				} catch (NoSuchFileException e) {
				} catch (IOException e) {
					LOG.error(e.getLocalizedMessage()
				}
