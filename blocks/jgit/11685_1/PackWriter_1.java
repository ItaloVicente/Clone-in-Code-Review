	private void cutDeltaChains(BlockList<ObjectToPack> list)
			throws IOException {
		int max = config.getMaxDeltaDepth();
		for (int idx = list.size() - 1; idx >= 0; idx--) {
			int d = 0;
			ObjectToPack b = list.get(idx).getDeltaBase();
			while (b != null) {
				if (d < b.getChainLength())
					break;
				b.setChainLength(++d);
				if (d >= max && b.isDeltaRepresentation()) {
					reselectNonDelta(b);
					break;
				}
				b = b.getDeltaBase();
			}
		}
		if (config.isDeltaCompress()) {
			for (ObjectToPack otp : list)
				otp.clearChainLength();
		}
	}

