	}

	private void redoSearchForReuse(final ObjectToPack otp) throws IOException
			MissingObjectException {
		otp.clearDeltaBase();
		otp.clearReuseAsIs();
		reuseSupport.selectObjectRepresentation(this
