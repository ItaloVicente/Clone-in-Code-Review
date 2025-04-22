        if (fIgnoreWildCards) {
			return (end - start == fLength)
                    && fPattern.regionMatches(fIgnoreCase, 0, text, start,
                            fLength);
		}
        int segCount = fSegments.length;
        if (segCount == 0 && (fHasLeadingStar || fHasTrailingStar)) {
			return true;
