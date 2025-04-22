		if (tips == null) {
			tips = new HashSet<ObjectId>();
			for (String idString : info.getTipList().getObjectNameList())
				tips.add(ObjectId.fromString(idString));
			tips = Collections.unmodifiableSet(tips);
		}
		return tips;
