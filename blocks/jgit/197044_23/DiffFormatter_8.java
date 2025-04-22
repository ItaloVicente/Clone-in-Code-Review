	private void writeLiteralPatch(ByteArrayOutputStream buf
			throws IOException {
		buf.write(encodeASCII("literal "
				+ rawData.length

		try (BinaryHunkOutputStream encoder =
					 new BinaryHunkOutputStream(buf)) {
			Deflater deflater = new Deflater(1);
			deflater.setInput(rawData);
			deflater.finish();

			byte[] buffer = new byte[8192];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				encoder.write(buffer
			}
			encoder.close();

			buf.write('\n');
		} catch (Exception ex) {
			throw new IOException(ex);
		}
	}

	private void writeDeltaPatch(ByteArrayOutputStream buf
					byte[] bData) throws IOException {
		Deflater deflater = new Deflater(1);
		deflater.setInput(bData);
		deflater.finish();
		byte[] buffer = new byte[8192];
		while (!deflater.finished()) {
			deflater.deflate(buffer);
		}

		int deflateSize = deflater.getTotalOut();
		ByteArrayOutputStream bos = new	ByteArrayOutputStream();
		DeltaIndex aIdx = new DeltaIndex(aData);
		if (aIdx.encode(bos
			buf.write(encodeASCII("delta "

			try (BinaryHunkOutputStream encoder =
						 new BinaryHunkOutputStream(buf)) {
				deflater = new Deflater(1);
				deflater.setInput(bos.toByteArray());
				deflater.finish();

				buffer = new byte[8192];
				while (!deflater.finished()) {
					int count = deflater.deflate(buffer);
					encoder.write(buffer
				}
				encoder.close();

				buf.write('\n');
			} catch (Exception ex) {
				throw new IOException(ex);
			}
		} else {
			writeLiteralPatch(buf
		}
	}

