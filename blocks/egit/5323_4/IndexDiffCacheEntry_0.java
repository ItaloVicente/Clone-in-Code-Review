						try {
							Ref ref = event.getRepository().getRef(Constants.HEAD);
							ObjectId objectId = ref.getObjectId();

							if (objectId != null) {
								boolean skipThis = (objectId
										.equals(lastCommitId));
								lastCommitId = objectId;

								if (skipThis)
									return;
							}
						} catch (IOException e) {
						}
