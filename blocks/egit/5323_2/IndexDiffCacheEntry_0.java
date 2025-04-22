						try {
							Ref ref = event.getRepository().getRef(Constants.HEAD);
							ObjectId objectId = ref.getObjectId();

							boolean skipThis = (objectId.equals(lastCommitId));
							lastCommitId = objectId;

							if(skipThis)
								return;
						} catch (IOException e) {
						}
