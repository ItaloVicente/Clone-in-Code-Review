		TransportLocal t = new TransportLocal(src, uriOf(dst), dst.getDirectory()) {
			@Override
			ReceivePack createReceivePack(final Repository db) {
				db.close();
				dst.incrementOpen();

				final ReceivePack rp = super.createReceivePack(dst);
				rp.setCheckReceivedObjects(true);
				rp.setCheckReferencedObjectsAreReachable(true);
				rp.setAdvertiseRefsHook(new HidePrivateHook());
				return rp;
			}
		};
