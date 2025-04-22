					try {
						if (!cmd.getNewId().equals(ObjectId.zeroId())) {
							walk.parseAny(cmd.getNewId());
						}
					} catch (MissingObjectException e) {
						cmd.setResult(ReceiveCommand.Result.REJECTED_MISSING_OBJECT);
						continue;
					}
