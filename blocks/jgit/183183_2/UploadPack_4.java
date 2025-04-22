	private void objectInfo(PacketLineOut pckOut) throws IOException {
		ProtocolV2Parser parser = new ProtocolV2Parser(transferConfig);
		ObjectInfoRequest req = parser.parseObjectInfoRequest(pckIn);

		protocolV2Hook.onObjectInfo(req);

		ObjectReader or = getRepository().newObjectReader();


		for (String objectID : req.getObjectIDs()) {
			ObjectId oid;
			try {
				oid = ObjectId.fromString(objectID);
			} catch (InvalidObjectIdException e) {
				continue;
			}
			long size = or.getObjectSize(oid
		}

		pckOut.end();
	}

