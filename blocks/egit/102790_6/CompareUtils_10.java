					blobId);
			if (nextFile != null) {
				String encoding = CompareCoreUtils.getResourceEncoding(db,
						gitPath);
				right = new FileRevisionTypedElement(nextFile, encoding);
			}
