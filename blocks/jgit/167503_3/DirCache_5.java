		int versionCode = NB.decodeInt32(hdr
		DirCacheVersion ver = DirCacheVersion.fromInt(versionCode);
		if (ver == null) {
			throw new CorruptObjectException(
					MessageFormat.format(JGitText.get().unknownDIRCVersion
							Integer.valueOf(versionCode)));
		}
