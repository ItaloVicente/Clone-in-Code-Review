			dce.setFileMode(newMode == FileMode.MISSING.getBits()
					? FileMode.REGULAR_FILE : FileMode.fromBits(newMode));
			if (mergedFile != null) {
				dce.setLastModified(
						nonNullRepo().getFS().lastModifiedInstant(mergedFile));
				dce.setLength((int) mergedFile.length());
			}
			dce.setObjectId(insertMergeResult(rawMerged
			builder.add(dce);
