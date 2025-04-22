	private InputStream handleAutoCRLF(InputStream in) {
		AutoCRLF autoCRLF = getOptions().getAutoCRLF();
		if (autoCRLF == AutoCRLF.TRUE || autoCRLF == AutoCRLF.INPUT) {
			in = new EolCanonicalizingInputStream(in, true);
		}
		return in;
