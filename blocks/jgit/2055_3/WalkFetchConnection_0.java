				try {
					if (pack.tmpIdx != null)
						FileUtils.delete(pack.tmpIdx);
				} catch (IOException e) {
					throw new TransportException(e.getMessage()
				}
