		server.getConfig().setBoolean("uploadpack"
				true);

		ByteArrayInputStream recvStream = uploadPackV2((UploadPack up) -> {
			up.setCachedPackUriProvider(new CachedPackUriProvider() {
				@Override
				public PackInfo getInfo(CachedPack pack
						Collection<String> protocolsSupported)
						throws IOException {
					assertThat(protocolsSupported
					if (!protocolsSupported.contains("https"))
						return null;
					return new PackInfo("myhash"
				}

			});
		}
				"want " + commit2.getName() + "\n"
				"packfile-uris https\n"
