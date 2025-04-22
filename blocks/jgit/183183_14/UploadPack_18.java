	private void objectInfo(PacketLineOut pckOut) throws IOException {
		ProtocolV2Parser parser = new ProtocolV2Parser(transferConfig);
		ObjectInfoRequest req = parser.parseObjectInfoRequest(pckIn);

		protocolV2Hook.onObjectInfo(req);

		ObjectReader or = getRepository().newObjectReader();


		for (ObjectId oid : req.getObjectIDs()) {
			long size;
			try {
				size = or.getObjectSize(oid
			} catch (MissingObjectException e) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().missingObject
			}

		}

		pckOut.end();
	}

