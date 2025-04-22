				Repository repo = (Repository) element;
				List<String> refs = new LinkedList<String>(repo.getAllRefs()
						.keySet());

				List<Ref> additionalRefs;
				try {
					additionalRefs = repo.getRefDatabase().getAdditionalRefs();
				} catch (IOException e) {
					additionalRefs = null;
				}
				if (additionalRefs != null)
					for (Ref ref : additionalRefs)
						refs.add(ref.getName());

