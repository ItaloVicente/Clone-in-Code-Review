				Optional<Ref> match = tags.stream()
						.filter(tag -> matcher.matches(tag.getName(), false,
								false))
						.findFirst();
				if (match.isPresent()) {
					return match;
				}
