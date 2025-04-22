	ObjectInfoRequest parseObjectInfoRequest(PacketLineIn pckIn)
			throws PackProtocolException
		ObjectInfoRequest.Builder builder = ObjectInfoRequest.builder();
		List<ObjectId> objectIDs = new ArrayList<>();

		String line = pckIn.readString();

		if (PacketLineIn.isEnd(line)) {
			return builder.build();
		}

			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().unexpectedPacketLine
		}

		for (String line2 : pckIn.readStrings()) {
				ObjectId oid;
				try {
				} catch (InvalidObjectIdException e) {
					continue;
				}
				objectIDs.add(oid);
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine
			}
		}

		return builder.setObjectIDs(objectIDs).build();
	}
