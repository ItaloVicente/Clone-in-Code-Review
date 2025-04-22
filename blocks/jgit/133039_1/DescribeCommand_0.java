				Stream<Ref> m = tags.stream().filter(
						tag -> matcher.matches(tag.getName()
				matchingTags = Stream.of(matchingTags
			}
			Optional<Ref> match = matchingTags.sorted(TAG_TIE_BREAKER)
					.findFirst();
			if (match.isPresent()) {
				return match;
