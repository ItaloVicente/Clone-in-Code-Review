		List<String> references = new ArrayList<>(projects.length);
		for (int i = 0; i < projects.length; i++) {
			String reference = asReference(projects[i]);
			if(reference != null){
				references.add(reference);
			}
		}
		return references.toArray(new String[references.size()]);
