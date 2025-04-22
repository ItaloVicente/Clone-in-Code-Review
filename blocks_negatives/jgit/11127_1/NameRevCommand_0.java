		for (Ref ref : repo.getRefDatabase().getRefs(prefix).values()) {
			if (ref.getObjectId() == null)
				continue;
			RevObject o = walk.parseAny(ref.getObjectId());
			while (o instanceof RevTag) {
				RevTag t = (RevTag) o;
				nonCommits.put(o, ref.getName());
				o = t.getObject();
				walk.parseHeaders(o);
			}
			if (o instanceof NameRevCommit) {
				NameRevCommit c = (NameRevCommit) o;
				if (c.tip == null)
					c.tip = ref.getName();
				pending.add(c);
			} else if (!nonCommits.containsKey(o))
				nonCommits.put(o, ref.getName());
