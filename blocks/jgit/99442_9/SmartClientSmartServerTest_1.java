					String targetContext = "/git";
					matcher = targetPattern.matcher(urlString);
					if (matcher.find()) {
						urlString = urlString.substring(0
								+ '/' + urlString.substring(matcher.end());
						targetContext = matcher.group(1);
					}
					urlString = urlString.replace("/redirect"
