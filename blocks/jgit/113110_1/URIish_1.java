			String portString = matcher.group(5);
				rawPath = cleanLeadingSlashes(
								+ n2e(matcher.group(6)) + n2e(matcher.group(7))
						scheme);
			} else {
				host = unescape(matcher.group(4));
				if (portString != null) {
					port = portString.length() > 0 ? new Integer(portString)
							: new Integer(-1);
				}
				rawPath = cleanLeadingSlashes(
						n2e(matcher.group(6)) + n2e(matcher.group(7))
			}
