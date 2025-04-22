						tag -> {
							matcher.append(
									tag.getName().substring(R_TAGS.length()));
							boolean result = matcher.isMatch();
							matcher.reset();
							return result;
						});
