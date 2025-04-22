	ObjectInfoRequest parseObjectInfoRequest(PacketLineIn pckIn)
			throws PackProtocolException
		ObjectInfoRequest.Builder builder = ObjectInfoRequest.builder();
		List<String> objectIDs = new ArrayList<>();

		String line = pckIn.readString();

		if (PacketLineIn.isEnd(line)) {
			return builder.build();
		}

			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().unexpectedPacketLine
		}

		for (String line2 : pckIn.readStrings()) {
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine
			}
		}

		return builder.setObjectIDs(objectIDs).build();
	}
