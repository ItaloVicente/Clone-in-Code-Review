		if (o instanceof NameRevCommit) {
			NameRevCommit c = (NameRevCommit) o;
			if (c.tip == null)
				c.tip = ref.getName();
			pending.add(c);
		} else if (!nonCommits.containsKey(o))
			nonCommits.put(o
