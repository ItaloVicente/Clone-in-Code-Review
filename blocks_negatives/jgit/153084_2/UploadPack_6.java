			if (pckOut.isUsingSideband()) {
				if (req instanceof FetchV2Request &&
						cachedPackUriProvider != null &&
						!((FetchV2Request) req).getPackfileUriProtocols().isEmpty()) {
					FetchV2Request reqV2 = (FetchV2Request) req;
					pw.setPackfileUriConfig(new PackWriter.PackfileUriConfig(
							pckOut,
							reqV2.getPackfileUriProtocols(),
							cachedPackUriProvider));
