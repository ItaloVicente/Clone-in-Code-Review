					Ref ref;
					if (refName.equals("")) {
						ref = getRef(Constants.HEAD);
						if (ref == null)
							return null;
						if (ref.isSymbolic())
							ref = ref.getLeaf();
						if (ref.getObjectId() == null)
							return null;
					} else
						ref = getRef(refName);
					if (ref == null)
