				try {
					if (repository != null) {
						ObjectId id = entry.getObjectId();
						ObjectLoader loader = repository.open(id);
						if (loader != null)
							entry.setLength((int) loader.getSize());
					}
				} catch (IOException e) {
				}
