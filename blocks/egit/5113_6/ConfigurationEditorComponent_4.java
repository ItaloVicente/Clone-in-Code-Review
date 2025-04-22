		}

		public Object[] getChildren(Object o) {
			if (children == null)
				if (config != null) {
					List<Section> sections = new ArrayList<Section>();
					Set<String> sectionNames = config.getSections();
					for (String sectionName : sectionNames)
						sections.add(new Section(this, sectionName));
					Collections.sort(sections, new Comparator<Section>() {

						public int compare(Section o1, Section o2) {
							return o1.name.compareTo(o2.name);
						}
					});
					children = sections.toArray(new Section[sections.size()]);
				} else
					children = new Section[0];
			return children;
		}

		public Entry getEntry(String sectionName, String subsectionName,
				String entryName) {
			for (Object child : getChildren(this)) {
				Section section = (Section) child;
				if (sectionName.equals(section.name))
					return section.getEntry(subsectionName, entryName);
			}
			return null;
		}
	}

	private final static class Section extends WorkbenchAdapter {
		private final String name;

		private final GitConfig parent;

		private Object[] children;

		Section(GitConfig parent, String name) {
			this.parent = parent;
