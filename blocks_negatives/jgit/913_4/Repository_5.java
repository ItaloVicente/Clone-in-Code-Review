					ref = mapObject(refId, null);
				}
				while (ref instanceof Tag) {
					Tag tag = (Tag)ref;
					refId = tag.getObjId();
					ref = mapObject(refId, null);
