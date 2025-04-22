							Tag tag = new Tag(repo);
							tag.setMessage("test tag"); // message should be obtained from user using eg. text widget
							tag.setTagger(new PersonIdent(repo));
							tag.setTag(labelDialog.getValue());
							ObjectId startAt;
							if (refName == null)
								startAt = repo.resolve(Constants.HEAD);
							else
								startAt = repo.resolve(refName);
							tag.setObjId(startAt);
							tag.setType(Constants.typeString(repo.openObject(startAt).getType()));

							ObjectWriter objWriter = new ObjectWriter(repo);
							tag.setTagId(objWriter.writeTag(tag));

							RefUpdate tagRef = repo.updateRef(newRefName);
							tagRef.setNewObjectId(tag.getTagId());
							tagRef.setRefLogMessage("tag: " + newRefName, false); //$NON-NLS-1$
							tagRef.update();
