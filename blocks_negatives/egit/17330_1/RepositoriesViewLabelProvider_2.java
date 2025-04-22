					ObjectId id = node.getRepository().resolve(refName);
					if (id == null)
						return image;
					RevWalk rw = new RevWalk(node.getRepository());
					try {
						compareString = rw.parseTag(id).getObject().name();
					} catch (IncorrectObjectTypeException e) {
						compareString = id.name();
					} finally {
						rw.release();
					}
