				try {
					Files.delete(f.toPath());
				} catch (IOException e) {
					if (!SystemReader.getInstance().isWindows())
						throw e;
				}
