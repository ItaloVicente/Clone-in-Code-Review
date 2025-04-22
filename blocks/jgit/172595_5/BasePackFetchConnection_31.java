
	private static class FetchStateV2 {

		long havesToSend = 32;

		long havesTotal;

		long havesWithoutAck;

		void incHavesToSend(boolean statelessRPC) {
			if (statelessRPC) {
				if (havesToSend < 16384) {
					havesToSend *= 2;
				} else {
					havesToSend = havesToSend * 11 / 10;
				}
			} else {
				havesToSend += 32;
			}
		}
	}
