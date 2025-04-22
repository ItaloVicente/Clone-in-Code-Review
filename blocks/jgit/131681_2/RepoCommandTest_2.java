				String idStr = ref + ":" + path;
				ObjectId blobId = repo.resolve(idStr);
				if (blobId == null) {
					throw new RefNotFoundException(
							String.format("repo %s does not have %s"
									repo.toString()
				}
				return reader.open(blobId).getCachedBytes(Integer.MAX_VALUE);
