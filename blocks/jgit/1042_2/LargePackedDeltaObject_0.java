		if (type == Constants.OBJ_BAD) {
			WindowCursor wc = new WindowCursor(db);
			try {
				type = pack.getObjectType(wc
			} catch (IOException packGone) {
				try {
					type = wc.open(getObjectId()).getType();
				} catch (IOException packGone2) {
				}
			} finally {
				wc.release();
			}
		}
