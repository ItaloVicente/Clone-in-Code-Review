			assertEquals(expected.length

			for (int bufferSize = 1; bufferSize < 10; bufferSize++) {
				final byte[] buffer = new byte[bufferSize];
				try (InputStream bis2 = new ByteArrayInputStream(input);
						InputStream cis2 = new AutoLFInputStream(bis2
								detectBinary)) {

					int read = 0;
					for (int readNow = cis2.read(buffer
							buffer.length); readNow != -1
									&& read < expected.length; readNow = cis2
											.read(buffer
						for (int index2 = 0; index2 < readNow; index2++) {
							assertEquals(expected[read + index2]
									buffer[index2]);
						}
						read += readNow;
					}

					assertEquals(expected.length
