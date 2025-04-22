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
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine
			}


			try {
				objectIDs.add(ObjectId.fromString(oidStr));
			} catch (InvalidObjectIdException e) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidObject
			}
		}

		return builder.setObjectIDs(objectIDs).build();
	}
