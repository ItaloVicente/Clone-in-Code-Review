					dirOnly
			if (i > 0) {
				final IMatcher last = matchers.get(matchers.size() - 1);
				if (isWild(matcher) && isWild(last))
					matchers.remove(matchers.size() - 1);
			}

