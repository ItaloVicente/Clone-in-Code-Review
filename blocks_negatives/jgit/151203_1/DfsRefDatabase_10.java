			} else {
				return new ObjectIdRef.PeeledNonTag(
						leaf.getStorage(),
						leaf.getName(),
						leaf.getObjectId(),
						hasVersioning() ? leaf.getUpdateIndex()
								: UNDEFINED_UPDATE_INDEX);
