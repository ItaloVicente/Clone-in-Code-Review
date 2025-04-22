				if (proposals != null)
					for (final Ref ref : proposals) {
						final String shortenedName = Repository
								.shortenRefName(ref.getName());
						if (pattern != null
								&& !pattern.matcher(ref.getName()).matches()
								&& !pattern.matcher(shortenedName).matches())
							continue;

						IContentProposal propsal = new RefContentProposal(
								repository, ref);
						resultList.add(propsal);
