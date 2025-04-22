				packOut.seek(pos);

				InputStream fileStream = new FilterInputStream(
						Channels.newInputStream(packOut.file.getChannel())) {
							@Override
							public int read() throws IOException {
								packOut.atEnd = false;
								return super.read();
							}

							@Override
							public int read(byte[] b) throws IOException {
								packOut.atEnd = false;
								return super.read(b);
							}

							@Override
							public int read(byte[] b
								packOut.atEnd = false;
								return super.read(b
							}

							@Override
							public void close() {
							}
						};
