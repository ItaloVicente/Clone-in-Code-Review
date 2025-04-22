		Object[] markers = (Object[]) object;
		lazyInit(markers);

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOut);

		byte[] bytes = null;

		try {
			out.writeInt(markers.length);

			for (Object marker : markers) {
				writeMarker((IMarker) marker, out);
			}
			out.close();
			bytes = byteOut.toByteArray();
		} catch (IOException e) {
		}

		if (bytes != null) {
			super.javaToNative(bytes, transferData);
		}
	}

	private void lazyInit(Object[] markers) {
		if (workspace == null) {
			if (markers != null && markers.length > 0) {
				this.workspace = ((IMarker) markers[0]).getResource()
						.getWorkspace();
			}
		}
	}

	@Override
