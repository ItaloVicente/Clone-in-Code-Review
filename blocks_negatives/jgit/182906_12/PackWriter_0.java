				for (CachedPack pack : cachedPacks) {
					CachedPackUriProvider.PackInfo packInfo = p.getInfo(
							pack, packfileUriConfig.protocolsSupported);
					if (packInfo != null) {
						o.writeString(packInfo.getHash() + ' ' +
								packInfo.getUri() + '\n');
						stats.offloadedPackfiles += 1;
						stats.offloadedPackfileSize += packInfo.getSize();
					} else {
						unwrittenCachedPacks.add(pack);
