	LsRefsV2Request parseLsRefRequest(PacketLineIn pckIn)
			throws PackProtocolException
		LsRefsV2Request.Builder builder = LsRefsV2Request.builder();
		List<String> prefixes = new ArrayList<>();
		String line = pckIn.readString();
		if (line == PacketLineIn.DELIM) {
			while ((line = pckIn.readString()) != PacketLineIn.END) {
					builder.setPeel(true);
					builder.setSymrefs(true);
				} else {
					throw new PackProtocolException(MessageFormat
							.format(JGitText.get().unexpectedPacketLine
				}
			}
		} else if (line != PacketLineIn.END) {
			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().unexpectedPacketLine
		}

		return builder.setRefPrefixes(prefixes).build();
	}

