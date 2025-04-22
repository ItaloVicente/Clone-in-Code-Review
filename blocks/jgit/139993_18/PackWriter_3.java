			List<CachedPack> unwrittenCachedPacks;

			if (packfileUriConfig != null) {
				unwrittenCachedPacks = new ArrayList<>();
				CachedPackUriProvider p = packfileUriConfig.cachedPackUriProvider;
				PacketLineOut o = packfileUriConfig.pckOut;

				o.writeString("packfile-uris\n");
				for (CachedPack pack : cachedPacks) {
					CachedPackUriProvider.PackInfo packInfo = p.getInfo(
							pack
					if (packInfo != null) {
						o.writeString(packInfo.getHash() + ' ' +
								packInfo.getUri() + '\n');
					} else {
						unwrittenCachedPacks.add(pack);
					}
				}
				packfileUriConfig.pckOut.writeDelim();
				packfileUriConfig.pckOut.writeString("packfile\n");
			} else {
				unwrittenCachedPacks = cachedPacks;
			}

