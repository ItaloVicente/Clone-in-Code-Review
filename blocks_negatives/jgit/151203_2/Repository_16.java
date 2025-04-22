						else {
							String previousCheckout = resolveReflogCheckout(-Integer
									.parseInt(time));
							if (ObjectId.isId(previousCheckout))
								rev = parseSimple(rw, previousCheckout);
							else
								name = previousCheckout;
