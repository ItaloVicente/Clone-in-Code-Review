						user = matcher.group(1);
						pass = matcher.group(2);
						host = matcher.group(3);
						path = matcher.group(4);
					} else {
						matcher = LOCAL_FILE.matcher(s);
						if (matcher.matches()) {
							path = matcher.group(1);
						} else
							throw new URISyntaxException(s
									JGitText.get().cannotParseGitURIish);
					}
