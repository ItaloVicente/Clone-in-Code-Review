			} catch (ServiceMayNotContinueException noPack) {
				throw noPack;
			} catch (IOException err) {
				try {
					reportInternalServerErrorOverSideband();
				} catch (IOException e) {
					err.addSuppressed(e);
					throw err;
