
		if (TrustPackedRefsModificationTime.AFTER_OPEN.equals(trustPackedRefsModTime)) {
			try (InputStream stream = Files.newInputStream(packedRefsFile.toPath())) {}
		}

		if (!TrustPackedRefsModificationTime.NEVER.equals(trustPackedRefsModTime)
				&& (!TrustPackedRefsModificationTime.UNSET.equals(trustPackedRefsModTime) || trustFolderStat)
				&& !curList.snapshot.isModified(packedRefsFile)) {
