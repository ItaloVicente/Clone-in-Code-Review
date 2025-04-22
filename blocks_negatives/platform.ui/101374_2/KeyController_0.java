				} finally {
					if (fileWriter != null) {
						try {
							fileWriter.close();
						} catch (final IOException e) {
						}
					}

