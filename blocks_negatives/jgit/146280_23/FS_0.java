			if (Files.exists(probe)) {
				try {
					Files.delete(probe);
				} catch (IOException e) {
					LOG.error(e.getLocalizedMessage(), e);
				}
