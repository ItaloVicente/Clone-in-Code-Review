
				if (isBoundary && !clientShallowCommits.contains(c)) {
					shallowFunc.accept(c.copy());
				}

				if (!isBoundary && clientShallowCommits.remove(c)) {
					unshallowFunc.accept(c.copy());
				}
