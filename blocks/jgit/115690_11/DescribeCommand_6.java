			Map<ObjectId
					.filter(ref -> {
						ObjectId id = ref.getObjectId();
						try {
							return Boolean.TRUE.equals(allTags)
									|| (id != null && (w.parseTag(id) != null));
						} catch (IOException e) {
							return false;
						}
					}).collect(Collectors.groupingBy(this::getObjectIdFromRef));
