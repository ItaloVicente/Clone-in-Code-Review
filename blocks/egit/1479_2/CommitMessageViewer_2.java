				if (currentEncoding == null)
					d.append(toString());

				else try {
					d.append(toString(currentEncoding));
				} catch (UnsupportedEncodingException e) {
					d.append(toString());
				}
