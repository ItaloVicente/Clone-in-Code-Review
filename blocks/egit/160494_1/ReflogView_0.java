			boolean needInputUpdate = repoChanged || needRefUpdate;
			if (!needInputUpdate) {
				if (currentInput instanceof ReflogInput) {
					try {
						ObjectId currentObjectId = null;
						Object[] currentChildren = ((ReflogInput) currentInput)
								.getChildren(null);
						if (currentChildren != null
								&& currentChildren.length > 0) {
							ReflogItem item = (ReflogItem) currentChildren[0];
							currentObjectId = item.getObjectId();
						}
						ObjectId inputObjectId = null;
						if (currentObjectId != null) {
							Ref inputRef = input.getRepository()
									.findRef(input.getRef());
							if (inputRef != null) {
								inputObjectId = inputRef.getTarget()
										.getObjectId();
							}
						}
						needInputUpdate = currentObjectId == null
								|| inputObjectId == null
								|| !currentObjectId.equals(inputObjectId);
					} catch (IOException e) {
						Activator.logError(UIText.ReflogView_ErrorOnOpenCommit,
								e);
					}
				} else {
					needInputUpdate = true;
				}
			}
			if (needInputUpdate) {
				refLogTreeViewer.setInput(input);
			}
