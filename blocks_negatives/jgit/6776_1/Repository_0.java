							if (ref.isSymbolic())
								ref = ref.getLeaf();
							if (ref.getObjectId() == null)
								return null;
						} else
							ref = getRef(refName);
						if (ref == null)
							return null;
