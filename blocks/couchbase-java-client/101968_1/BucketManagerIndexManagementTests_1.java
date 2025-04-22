                LOGGER.info("Unable to open/use bucket" + e.getMessage());
				try {
					Thread.sleep(100);
				} catch (InterruptedException iex) {
					Thread.currentThread().interrupt();
					throw new RuntimeException(iex);
				}
