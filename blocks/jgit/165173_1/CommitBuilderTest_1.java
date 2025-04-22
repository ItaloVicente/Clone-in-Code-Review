	public void writeGpgSignatureString() throws Exception {
		assertGpgSignatureStringOutcome(SIGNATURE
	}

	@Test
	public void writeGpgSignatureStringTrailingLF() throws Exception {
		assertGpgSignatureStringOutcome(SIGNATURE + '\n'
	}

	@Test
	public void writeGpgSignatureStringCRLF() throws Exception {
		assertGpgSignatureStringOutcome(SIGNATURE.replaceAll("\n"
				EXPECTED);
	}

	@Test
	public void writeGpgSignatureStringTrailingCRLF() throws Exception {
		assertGpgSignatureStringOutcome(
				SIGNATURE.replaceAll("\n"
