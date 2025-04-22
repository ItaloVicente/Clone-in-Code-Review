	private void writeObjectImpl(PackOutputStream out
			throws IOException {
		if (otp.wantWrite()) {
			reuseDeltas = false;
			otp.clearDeltaBase();
			otp.clearReuseAsIs();
			reuseSupport.selectObjectRepresentation(this
					NullProgressMonitor.INSTANCE
					Collections.singleton(otp));
		}
