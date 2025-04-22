				Optional<Ref> match = tags.stream()
						.filter(tag -> matcher.matches(tag.getName(), false,
								false))
						.sorted(TAG_TIE_BREAKER)
						.findFirst();
				if (match.isPresent()) {
					return match;
				}
