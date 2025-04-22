						ref = mapObject(refId, null);
						while (ref instanceof Tag) {
							Tag tag = (Tag)ref;
							refId = tag.getObjId();
							ref = mapObject(refId, null);
						}
						if (!(ref instanceof Commit))
							throw new IncorrectObjectTypeException(refId, Constants.TYPE_COMMIT);
						for (j=i+1; j<rev.length; ++j) {
