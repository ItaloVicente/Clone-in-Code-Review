		} catch (ServiceMayNotContinueException noPack) {
			if (sideband && noPack.getMessage() != null) {
				noPack.setOutput();
				@SuppressWarnings("resource" /* java 7 */)
				SideBandOutputStream err = new SideBandOutputStream(
						SideBandOutputStream.CH_ERROR,
						SideBandOutputStream.SMALL_BUF, rawOut);
				err.write(Constants.encode(noPack.getMessage()));
				err.flush();
			}
			throw noPack;
		}
