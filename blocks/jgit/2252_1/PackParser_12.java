			packDigest = null;
			baseById = null;
			baseByPos = null;
		} finally {
			try {
				if (readCurs != null)
					readCurs.release();
			} finally {
				readCurs = null;
