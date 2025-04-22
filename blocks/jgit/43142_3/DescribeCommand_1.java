			if (lucky != null) {
				if (!longDesc) {
					return lucky.getName().substring(R_TAGS.length());
				} else {
					ObjectId objectId = lucky.getPeeledObjectId();
					if (objectId == null) {
						objectId = lucky.getObjectId();
					}
					return String.format("%s-0-g%s"
							lucky.getName().substring(R_TAGS.length())
									.getObjectReader().abbreviate(objectId)
									.name());
				}
			}
