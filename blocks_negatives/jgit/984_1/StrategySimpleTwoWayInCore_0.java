				try {
					resultTree = cache.writeTree(odi);
					odi.flush();
				} finally {
					odi.release();
				}
