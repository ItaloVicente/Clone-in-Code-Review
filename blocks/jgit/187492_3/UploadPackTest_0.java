				(UploadPack up) -> {
					up.setCachedPackUriProvider(new CachedPackUriProvider() {
						@Override
						public PackInfo getInfo(ICachedPack pack
								Collection<String> protocolsSupported)
								throws IOException {
							assertThat(protocolsSupported
							if (!protocolsSupported.contains("https"))
								return null;
							return new PackInfo("myhash"
						}

					});
				}
				"command=fetch\n"
				PacketLineIn.delimiter()
				"want " + commit2.getName() + "\n"
				"sideband-all\n"
				"packfile-uris https\n"
				"done\n"
				PacketLineIn.end());
