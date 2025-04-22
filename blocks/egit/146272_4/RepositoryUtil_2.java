								ref = repository.getRefDatabase().peel(ref);
							}
							if (ref != null) {
								ObjectId id = ref.getPeeledObjectId();
								if (id != null
										&& id.getName().equals(commitId)) {
									return checkoutEntry.getToBranch();
