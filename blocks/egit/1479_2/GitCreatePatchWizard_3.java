							if (currentEncoding == null)
								sb.append(toString());
							else try {
								sb.append(toString(currentEncoding));
							} catch (UnsupportedEncodingException e) {
								sb.append(toString());
							}
