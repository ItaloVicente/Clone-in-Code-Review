					}
				}
				return result;
			}

			@Override
			public Collection<Ref> filter(Collection<Ref> refs) {
				Set<Ref> result = new HashSet<>();
				for (Ref ref : refs) {
					if (add(ref)) {
						result.add(ref);
					}
