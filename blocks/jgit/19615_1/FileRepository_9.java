				if (primary == null)
					r.addAll(repo.getAdditionalHaves(objectDatabase));
				else if (!d.in(primary.myAlternates())) {
					primary.addAlternate(d);
					r.addAll(repo.getAdditionalHaves(primary));
				}
