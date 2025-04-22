				if (!asText && !asBinary && ent.getChangeType() != RENAME) {
					buf.write(encodeASCII(
							String.format("Binary files " +
									"a/%s and b/%s differ\n"
									ent.getOldPath()
									ent.getNewPath()

					editList = new EditList();
					type = PatchType.BINARY;

					res.header = new FileHeader(buf.toByteArray()
							editList
					return res;
				}
				try {
					aRaw = openBinary(OLD
					bRaw = openBinary(NEW
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				if (ent.getChangeType() == ADD) {
					if (asBinary) {
						assert bRaw != null;
						buf.write(encodeASCII("literal "
								+ bRaw.content.length
						try (BinaryHunkOutputStream encoder =
									 new BinaryHunkOutputStream(buf)) {

							Deflater deflater = new Deflater(1);
							deflater.setInput(bRaw.content);
							deflater.finish();

							byte[] buffer = new byte[8192];
							while (!deflater.finished()) {
								int count = deflater.deflate(buffer);
								encoder.write(buffer
							}
							encoder.close();

							buf.write('\n');
							buf.write('\n');

						} catch (Exception ex) {
							throw new IOException(ex);
						}

						editList = new EditList();
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					} else if (asText) {
						formatOldNewPaths(buf

						res.a = aRaw;
						res.b = bRaw;
						editList = diff(res.a
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					}
				} else if (ent.getChangeType() == MODIFY) {
					if (asBinary) {
						assert aRaw != null;
						assert bRaw != null;
						ByteArrayOutputStream forwardStream = new
								ByteArrayOutputStream();
						DeltaIndex aIdx = new DeltaIndex(aRaw.content);
						aIdx.encode(forwardStream
						buf.write(encodeASCII("delta "

						try (BinaryHunkOutputStream encoder = new
								BinaryHunkOutputStream(buf)) {
							Deflater deflater = new Deflater(1);
							deflater.setInput(forwardStream.toByteArray());
							deflater.finish();
							byte[] buffer = new byte[8192];
							while (!deflater.finished()) {
								int count = deflater.deflate(buffer);
								encoder.write(buffer
							}
						} catch (Exception ex) {
							throw new IOException(ex);
						}
						buf.write('\n');

						ByteArrayOutputStream reverseStream = new
								ByteArrayOutputStream();
						DeltaIndex bIdx = new DeltaIndex(aRaw.content);
						bIdx.encode(reverseStream
						buf.write(encodeASCII("delta "
						try (BinaryHunkOutputStream encoder =
									 new BinaryHunkOutputStream(buf)) {
							Deflater deflater = new Deflater(1);
							deflater.setInput(reverseStream.toByteArray());
							deflater.finish();
							byte[] buffer = new byte[8192];
							while (!deflater.finished()) {
								int count = deflater.deflate(buffer);
								encoder.write(buffer
							}
						} catch (Exception ex) {
							throw new IOException(ex);
						}
						buf.write('\n');

						editList = new EditList();
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					} else if (asText) {

						formatOldNewPaths(buf

						res.a = aRaw;
						res.b = bRaw;
						editList = diff(res.a
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					}
				} if (ent.getChangeType() == DELETE) {
					if (asBinary) {
						assert aRaw != null;
						buf.write('\n');

						buf.write(encodeASCII("literal "
								+ aRaw.content.length
						try (BinaryHunkOutputStream encoder =
									 new BinaryHunkOutputStream(buf)) {

							Deflater deflater = new Deflater(1);
							deflater.setInput(aRaw.content);
							deflater.finish();

							byte[] buffer = new byte[8192];
							while (!deflater.finished()) {
								int count = deflater.deflate(buffer);
								encoder.write(buffer
							}
							encoder.close();

							buf.write('\n');
							buf.write('\n');

						} catch (Exception ex) {
							throw new IOException(ex);
						}

						editList = new EditList();
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					} else if (asText) {
						formatOldNewPaths(buf

						res.a = aRaw;
						res.b = bRaw;
						editList = diff(res.a
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
								editList
						return res;
					}
				} else {
					editList = new EditList();
					type = PatchType.BINARY;

					res.header = new FileHeader(buf.toByteArray()
							editList
					return res;
				}
