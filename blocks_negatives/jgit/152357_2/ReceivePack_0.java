						else
							cmd.setType(
									ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
					} catch (MissingObjectException e) {
						cmd.setResult(Result.REJECTED_MISSING_OBJECT,
								e.getMessage());
