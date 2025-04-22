				if (!asText && !asBinary) {
					buf.write(encodeASCII(String.format("Binary files a/%s and b/%s differ\n"

					editList = new EditList();
					type = PatchType.BINARY;

					res.header = new FileHeader(buf.toByteArray()
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
						buf.write(encodeASCII(String.format("literal %s\n"
						try (BinaryHunkOutputStream encoder = new BinaryHunkOutputStream(buf)) {

							Deflater deflater = new Deflater(1);
							deflater.setInput(bRaw.content);
							deflater.finish();

							byte[] buffer = new byte[1024];
							while (!deflater.finished()) {
								int count = deflater.deflate(buffer);
								encoder.write(buffer
							}
							encoder.flush();


						} catch (Exception ex) {
							throw new RuntimeException(ex);
						}

						editList = new EditList();
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
						return res;
					}
					else if (asText) {
						res.a = aRaw;
						res.b = bRaw;
						editList = diff(res.a
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
						return res;
					}
				}
				else if (ent.getChangeType() == MODIFY) {
					if (asBinary) {
						assert bRaw != null;
						buf.write(encodeASCII(String.format("delta %s\n"
						buf.write(encodeASCII("Warning

						res.a = aRaw;
						res.b = bRaw;
						editList = new EditList();
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
						return res;
					}
					else if (asText) {
						res.a = aRaw;
						res.b = bRaw;
						editList = diff(res.a
						type = PatchType.BINARY;

						res.header = new FileHeader(buf.toByteArray()
						return res;
					}
				}
