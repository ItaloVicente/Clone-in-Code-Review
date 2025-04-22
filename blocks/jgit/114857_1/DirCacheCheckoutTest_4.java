					try (InputStream is = Files.newInputStream(file.toPath())) {
						byte[] buffer = new byte[(int) file.length()];
						int offset = 0;
						int numRead = 0;
						while (offset < buffer.length
								&& (numRead = is.read(buffer
										buffer.length - offset)) >= 0) {
							offset += numRead;
						}
						assertArrayEquals(
								"unexpected content for path " + path
										+ " in workDir. "
								buffer
