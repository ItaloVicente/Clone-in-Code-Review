		@Override
		protected void writePack(PackWriter pw) throws IOException {
			if (pckOut.isUsingSideband() && cachedPackUriProvider != null
					&& !req.getPackfileUriProtocols().isEmpty()) {
				pw.setPackfileUriConfig(new PackWriter.PackfileUriConfig(pckOut
						req.getPackfileUriProtocols()
			} else if (pckOut.isUsingSideband()) {
