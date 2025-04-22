	public boolean isGitSpecialFile() {
		if (currVisit == null)
			return false;
		return (currVisit.nameEnd - currVisit.namePtr) >= 4 &&
			currVisit.buf[currVisit.namePtr] == '.' &&
			currVisit.buf[currVisit.namePtr + 1] == 'g' &&
			currVisit.buf[currVisit.namePtr + 2] == 'i' &&
			currVisit.buf[currVisit.namePtr + 3] == 't';
	}

