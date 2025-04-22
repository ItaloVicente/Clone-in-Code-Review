			BlobObjectChecker checker = newBlobObjectChecker().orElse(null);
			if (checker == null) {
				checkBlob(raw);
			} else {
				checker.update(raw
				checker.endBlob(id);
			}
