		LsRemoteCommand command = Git.lsRemoteRepository().setRemote(remote)
				.setTimeout(timeout).setHeads(heads).setTags(tags);
		TreeSet<Ref> refs = new TreeSet<Ref>(new Comparator<Ref>() {

			public int compare(Ref r1
				return r1.getName().compareTo(r2.getName());
