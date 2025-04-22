			rw = super.preparePack(pw
			applyTags(pw
			return rw;
		}
	}

	private class V2PackSender extends PackSender<FetchV2Request> {
		private final PacketLineOut pckOut;

		private final List<ObjectId> deepenNots;
