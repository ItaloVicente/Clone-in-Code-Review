						&& !clientShallowCommits.contains(c)) {
					if (shallowCommits == null) {
					} else {
						shallowCommits.add(c.copy());
					}
				}
