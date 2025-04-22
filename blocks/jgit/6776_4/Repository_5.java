					if (time.matches("^-\\d+$")) {
						if (ref != null)
							throw new RevisionSyntaxException(revstr);
						else {
							String previousCheckout = resolveReflogCheckout(-Integer
									.parseInt(time));
							if (ObjectId.isId(previousCheckout))
								rev = parseSimple(rw
							else
								ref = getRef(previousCheckout);
						}
					} else {
						if (ref == null) {
							String refName = new String(revChars
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
								return null;
						}
						rev = resolveReflog(rw
					}
