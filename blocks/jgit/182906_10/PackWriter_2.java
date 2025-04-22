		int writePackAttempts = 0;
		WRITEPACK: for (;;) {
			try {
				List <CachedPack> unwrittenCachedPacks;

				if (packfileUriConfig != null) {
					unwrittenCachedPacks = new ArrayList <>();
					CachedPackUriProvider p = packfileUriConfig.cachedPackUriProvider;
					PacketLineOut o = packfileUriConfig.pckOut;

					for (CachedPack pack : cachedPacks) {
						CachedPackUriProvider.PackInfo packInfo = p.getInfo(
								pack
						if (packInfo != null) {
							o.writeString(packInfo.getHash() + ' ' +
									packInfo.getUri() + '\n');
							stats.offloadedPackfiles += 1;
							stats.offloadedPackfileSize += packInfo.getSize();
						} else {
							unwrittenCachedPacks.add(pack);
						}
					}
					packfileUriConfig.pckOut.writeDelim();
				} else {
					unwrittenCachedPacks = cachedPacks;
				}
