			Collection<RefSpec> refSpecs = new ArrayList<RefSpec>(1);
			if (tags)
				refSpecs.add(new RefSpec(
			if (heads)
			Collection<Ref> refs;
			Map<String
			FetchConnection fc = transport.openFetch();
