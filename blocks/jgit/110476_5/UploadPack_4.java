			if (blobMaxBytes >= 0) {
				pw.setBlobMaxBytes(blobMaxBytes);
				pw.setUseCachedPacks(false);
			} else {
				pw.setUseCachedPacks(true);
			}
