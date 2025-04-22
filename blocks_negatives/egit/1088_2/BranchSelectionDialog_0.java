						Ref startRef = repo.getRef(refName);
						ObjectId startAt = repo.resolve(refName);
						String startBranch;
						if (startRef != null)
							startBranch = refName;
						else
							startBranch = startAt.name();
						startBranch = repo.shortenRefName(startBranch);
