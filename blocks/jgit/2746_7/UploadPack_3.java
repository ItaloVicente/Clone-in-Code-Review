	public void sendAdvertisedRefs(final RefAdvertiser adv) throws IOException
			UploadPackMayNotContinueException {
		try {
			preUploadHook.onPreAdvertiseRefs(this);
		} catch (UploadPackMayNotContinueException fail) {
			if (fail.getMessage() != null) {
				adv.writeOne("ERR " + fail.getMessage());
				fail.setOutput();
			}
			throw fail;
		}

