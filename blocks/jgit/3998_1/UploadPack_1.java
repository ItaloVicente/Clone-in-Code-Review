			boolean success = false;
			OutputStream cacheOut = null;
			try {
				if (uploadPackCache != null) {
					cacheOut = uploadPackCache.newEntry(cacheOptions
							want
					if (cacheOut != null)
						packOut = new TeeOutputStream(packOut
				}
				pw.writePack(pm
				success = true;
			} finally {
				if (cacheOut != null)
					uploadPackCache.finishEntry(cacheOut
			}
