		if (SystemReader.getInstance().isWindows()) {
			c.setPackedGitMMAP(false);
		} else {
			c.setPackedGitMMAP(
					p.getBoolean(GitCorePreferences.core_packedGitMMAP,
							d.getBoolean(GitCorePreferences.core_packedGitMMAP,
									false)));
		}
