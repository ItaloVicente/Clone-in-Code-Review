			for (IMatcher matcher : matchers) {
				Optional<Ref> match = tags.stream()
						.filter(tag -> matcher.matches(tag.getName()
						.findFirst();
				if (match.isPresent()) {
					return match;
				}
			}
			return Optional.empty();
