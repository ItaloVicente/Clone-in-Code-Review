			case DELTA_DEFLATED: {
				byte[] base;
				try (InputStream in = inputSupplier.load()) {
					base = IO.readWholeStream(in
				}
				StreamSupplier supp = () -> new BinaryDeltaInputStream(base
						new InflaterInputStream(
								new BinaryHunkInputStream(new ByteArrayInputStream(
										hunk.getBuffer()
