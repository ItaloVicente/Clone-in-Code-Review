						else {
							if (ref.startsWith(Constants.R_TAGS))
								branch = null;
							else
								branch = node.getRepository().getRef(ref);
						}
