				if (req instanceof FetchV2Request &&
						cachedPackUriProvider != null &&
						!((FetchV2Request) req).getPackfileUriProtocols().isEmpty()) {
					FetchV2Request reqV2 = (FetchV2Request) req;
					@SuppressWarnings("null")
					@NonNull CachedPackUriProvider nonNull = cachedPackUriProvider;
					pw.setPackfileUriConfig(new PackWriter.PackfileUriConfig(
							pckOut
							reqV2.getPackfileUriProtocols()
							nonNull));
					pw.writePack(pm
							packOut);
				} else {
					pw.writePack(pm
				}
			} else {
				pw.writePack(pm
