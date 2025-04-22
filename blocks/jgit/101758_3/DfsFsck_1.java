						if (r.getLeaf().getName().startsWith(Constants.R_HEADS)) {
							if (tip.getType() != Constants.OBJ_COMMIT) {
								errors.getNonCommitHeads()
										.add(r.getLeaf().getName());
							}
						}
