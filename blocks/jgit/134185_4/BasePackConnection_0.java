			final ObjectId id;
			try {
				id  = ObjectId.fromString(line.substring(0
			} catch (InvalidObjectIdException e) {
				throw invalidRefAdvertisementLine(line);
			}
