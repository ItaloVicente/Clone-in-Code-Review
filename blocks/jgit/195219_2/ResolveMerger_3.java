			boolean hasSymlink = FileMode.SYMLINK.equals(modeO)
					|| FileMode.SYMLINK.equals(modeT);
			if (!hasSymlink) {
				try {
					result = contentMerge(base
							getContentMergeStrategy());
				} catch (BinaryBlobException e) {
				}
			}
			if (result == null) {
