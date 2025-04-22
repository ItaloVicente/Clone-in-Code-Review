
	protected void endOut() {
		if (outNeedsEnd && out != null) {
			try {
				outNeedsEnd = false;
				pckOut.end();
			} catch (IOException e) {
				try {
					out.close();
				} catch (IOException err) {
				} finally {
					out = null;
					pckOut = null;
				}
			}
		}
	}
