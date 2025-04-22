				try {
					if (readCurs != null)
						readCurs.release();
				} finally {
					readCurs = null;
				}

