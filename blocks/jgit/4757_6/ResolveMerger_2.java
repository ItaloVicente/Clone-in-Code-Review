	private int mergeFileModes(int modeB
		if (modeO == modeT)
			return modeO;
		if (modeB == modeO)
			return (modeT == FileMode.MISSING.getBits()) ? modeO : modeT;
		if (modeB == modeT)
			return (modeO == FileMode.MISSING.getBits()) ? modeT : modeO;
		return FileMode.MISSING.getBits();
	}

