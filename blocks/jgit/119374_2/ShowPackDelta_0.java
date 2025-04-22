		try (TemporaryBuffer.Heap raw = new TemporaryBuffer.Heap(
				bufArray.length);
				InflaterInputStream inf = new InflaterInputStream(
						new ByteArrayInputStream(bufArray
								bufArray.length))) {
			raw.copy(inf);
			return raw.toByteArray();
		}
