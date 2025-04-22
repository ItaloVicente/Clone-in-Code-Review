				if (dstIdx != null && (keepEmpty || entryCount > 0))
					writeIdx();

			} finally {
				try {
					if (readCurs != null)
						readCurs.release();
				} finally {
					readCurs = null;
				}

				try {
					inflater.release();
				} finally {
					inflater = null;
					objectDatabase.close();
