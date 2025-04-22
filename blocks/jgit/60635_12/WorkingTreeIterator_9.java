		if (cleanFilterCommandRef == null) {
			String cmd = null;
			if (state.walk != null) {
				cmd = state.walk
						.getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN);
			}
			cleanFilterCommandRef = new AtomicReference<String>(cmd);
		}
		return cleanFilterCommandRef.get();
	}

	public EolStreamType getEolStreamType() throws IOException {
		if (eolStreamTypeRef == null) {
			EolStreamType type=null;
			if (state.walk != null) {
				type=state.walk.getEolStreamType();
			} else {
				switch (getOptions().getAutoCRLF()) {
				case FALSE:
					type = EolStreamType.DIRECT;
					break;
				case TRUE:
				case INPUT:
					type = EolStreamType.AUTO_LF;
					break;
				}
			}
			eolStreamTypeRef = new AtomicReference<EolStreamType>(type);
