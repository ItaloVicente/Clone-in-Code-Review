					if (time.matches("^-\\d+$")) {
						if (name != null)
							throw new RevisionSyntaxException(revstr);
						else {
							String previousCheckout = resolveReflogCheckout(-Integer
									.parseInt(time));
							if (ObjectId.isId(previousCheckout))
								rev = parseSimple(rw
							else
								name = previousCheckout;
						}
					} else {
						if (name == null)
							name = new String(revChars
						if (name.equals(""))
							name = Constants.HEAD;
						Ref ref = getRef(name);
