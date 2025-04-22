			return FilterSpec.withBlobLimit(0);
			long blobLimit = -1;
			try {
				blobLimit = Long
			} catch (NumberFormatException e) {
			}
			if (blobLimit >= 0) {
				return FilterSpec.withBlobLimit(blobLimit);
