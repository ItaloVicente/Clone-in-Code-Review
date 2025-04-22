			List<CachedPack> unwrittenCachedPacks;

			if (packfileUriConfig != null) {
				unwrittenCachedPacks = new ArrayList<>();
				CachedPackUriProvider p = packfileUriConfig.cachedPackUriProvider;
				PacketLineOut o = packfileUriConfig.pckOut;

				o.writeString("packfile-uris\n");
				for (CachedPack pack : cachedPacks) {
					if (p.hasUri(pack
						o.writeString(p.getHashAndUri(pack) + '\n');
					} else {
						unwrittenCachedPacks.add(pack);
					}
				}
				packfileUriConfig.pckOut.writeDelim();
				packfileUriConfig.pckOut.writeString("packfile\n");
			} else {
				unwrittenCachedPacks = cachedPacks;
			}

