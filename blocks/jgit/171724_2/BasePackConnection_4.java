			final ObjectId id;
			try {
				id  = ObjectId.fromString(line.substring(0
			} catch (InvalidObjectIdException e) {
				PackProtocolException ppe = invalidRefAdvertisementLine(line);
				ppe.initCause(e);
				throw ppe;
			}
