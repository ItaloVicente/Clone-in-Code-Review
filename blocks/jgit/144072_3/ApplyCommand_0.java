		if (fh.getHunks() != null && fh.getHunks().size() > 0) {
			HunkHeader lastHunk = fh.getHunks().get(fh.getHunks().size() - 1);
			RawText lhrt = new RawText(lastHunk.getBuffer());
			return lhrt.getString(lhrt.size() - 1)
		}
		return false;
