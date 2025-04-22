			Collection<RefSpec> refSpecs = new ArrayList<RefSpec>(1);
			if (tags)
				refSpecs.add(new RefSpec(
			if (heads)
			Collection<Ref> refs;
			Map<String
			fc = transport.openFetch();
			refs = fc.getRefs();
			if (refSpecs.isEmpty())
				for (Ref r : refs)
					refmap.put(r.getName()
			else
				for (Ref r : refs)
					for (RefSpec rs : refSpecs)
						if (rs.matchSource(r)) {
