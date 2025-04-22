		Map<String, Ref> refsMap = getRepository().getRefDatabase().getRefs(
				getObject().toPortableString());
		for (Map.Entry<String, Ref> entry : refsMap.entrySet()) {
			if (entry.getValue().isSymbolic())
				continue;
			result.add(getObject().append(new Path(entry.getKey())));
