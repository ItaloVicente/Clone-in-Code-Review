				while (ref instanceof Tag) {
					Tag tag = (Tag)ref;
					refId = tag.getObjId();
					ref = mapObject(refId, null);
				}
				if (!(ref instanceof Commit))
					throw new IncorrectObjectTypeException(refId, Constants.TYPE_COMMIT);
