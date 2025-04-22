
		@Override
		protected void respond(HttpServletRequest req
				PacketLineOut pckOut
				ServiceNotEnabledException
			UploadPack up = (UploadPack) req.getAttribute(ATTRIBUTE_HANDLER);
			try {
				up.setBiDirectionalPipe(false);
				up.sendAdvertisedRefs(new PacketLineOutRefAdvertiser(pckOut)
			} finally {
				up.getRevWalk().close();
			}
		}
