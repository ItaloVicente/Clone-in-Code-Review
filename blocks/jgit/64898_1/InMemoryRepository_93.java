						ReceiveCommand.abort(cmds);
						return;
					}
				} else {
					ObjectId objectId = r.getObjectId();
					if (r.isSymbolic() || objectId == null
							|| !objectId.equals(c.getOldId())) {
						c.setResult(ReceiveCommand.Result.LOCK_FAILURE);
						ReceiveCommand.abort(cmds);
