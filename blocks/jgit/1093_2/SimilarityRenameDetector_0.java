	private int nameScore(String a
	    int aDirLen = a.lastIndexOf("/") + 1;
	    int bDirLen = b.lastIndexOf("/") + 1;

	    int dirMin = Math.min(aDirLen
	    int dirMax = Math.max(aDirLen

	    final int dirScoreLtr;
	    final int dirScoreRtl;

		if (dirMax == 0) {
			dirScoreLtr = 100;
			dirScoreRtl = 100;
		} else {
			int dirSim = 0;
			for (; dirSim < dirMin; dirSim++) {
				if (a.charAt(dirSim) != b.charAt(dirSim))
					break;
			}
			dirScoreLtr = (dirSim * 100) / dirMax;

			if (dirScoreLtr == 100) {
				dirScoreRtl = 100;
			} else {
				for (dirSim = 0; dirSim < dirMin; dirSim++) {
					if (a.charAt(aDirLen - 1 - dirSim) != b.charAt(bDirLen - 1
							- dirSim))
						break;
				}
				dirScoreRtl = (dirSim * 100) / dirMax;
			}
		}

		int fileMin = Math.min(a.length() - aDirLen
		int fileMax = Math.max(a.length() - aDirLen

		int fileSim = 0;
		for (; fileSim < fileMin; fileSim++) {
			if (a.charAt(a.length() - 1 - fileSim) != b.charAt(b.length() - 1
					- fileSim))
				break;
		}
		int fileScore = (fileSim * 100) / fileMax;

		return (((dirScoreLtr + dirScoreRtl) * 25) + (fileScore * 50)) / 100;
	}

