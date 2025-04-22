
			try {
				Collection<RefSpec> refSpecs = new ArrayList<RefSpec>(1);
				if (tags)
					refSpecs.add(new RefSpec(
							"refs/tags/*:refs/remotes/origin/tags/*"));
				if (heads)
					refSpecs.add(new RefSpec(
							"refs/heads/*:refs/remotes/origin/*"));
				Collection<Ref> refs;
				Map<String, Ref> refmap = new HashMap<String, Ref>();
				FetchConnection fc = transport.openFetch();
				try {
					refs = fc.getRefs();
					if (refSpecs.isEmpty())
						for (Ref r : refs)
