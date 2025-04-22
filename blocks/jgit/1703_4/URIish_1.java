				scheme = matcher.group(1);
				user = matcher.group(2);
				pass = matcher.group(3);
				host = matcher.group(4);
				if (matcher.group(5) != null)
					port = Integer.parseInt(matcher.group(5));
				path = cleanLeadingSlashes(
						n2e(matcher.group(6)) + n2e(matcher.group(7))
						scheme);
