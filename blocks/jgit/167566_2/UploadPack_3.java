			if (writeErrorsTOSideband) {
				SideBandOutputStream err = new SideBandOutputStream(
						SideBandOutputStream.CH_ERROR
						SideBandOutputStream.SMALL_BUF
				err.write(Constants.encode(message));
				err.flush();
				return;
			}
			new PacketLineOut(requireNonNull(rawOut))
		}

		public void setWriteErrorsTOSideband(boolean writeErrorsTOSideband) {
			this.writeErrorsTOSideband = writeErrorsTOSideband;
