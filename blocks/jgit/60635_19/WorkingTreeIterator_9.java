		if (cleanFilterCommandHolder == null) {
			String cmd = null;
			if (state.walk != null) {
				cmd = state.walk
						.getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN);
			}
			cleanFilterCommandHolder = new Holder<String>(cmd);
		}
		return cleanFilterCommandHolder.get();
	}

	public EolStreamType getEolStreamType() throws IOException {
		if (eolStreamTypeHolder == null) {
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
			eolStreamTypeHolder = new Holder<EolStreamType>(type);
