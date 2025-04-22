		if (size < 0) {
			WindowCursor wc = new WindowCursor(db);
			try {
				byte[] b = pack.getDeltaHeader(wc
				size = BinaryDelta.getResultSize(b);
			} catch (DataFormatException objectCorrupt) {
			} catch (IOException packGone) {
				try {
					size = wc.open(getObjectId()).getSize();
				} catch (IOException packGone2) {
				}
			} finally {
				wc.release();
			}
		}
