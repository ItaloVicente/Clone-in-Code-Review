	private static final class ConfigEditorContentProvider implements
			ITreeContentProvider {
		Config userConfig;

		public Object[] getElements(Object inputElement) {
			if (userConfig == null)
				return null;
			List<Section> sections = new ArrayList<Section>();
			Set<String> sectionNames = userConfig.getSections();
			for (String sectionName : sectionNames)
				sections.add(new Section(userConfig, sectionName));
			Collections.sort(sections, new Comparator<Section>() {

				public int compare(Section o1, Section o2) {
					return o1.name.compareTo(o2.name);
				}
			});
			return sections.toArray();

		}

		public void dispose() {
			userConfig = null;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			userConfig = (Config) newInput;
		}

		public Object[] getChildren(Object parentElement) {
			List<Object> result = new ArrayList<Object>();
			if (parentElement instanceof Section) {
				Section section = (Section) parentElement;
				Set<String> subSectionNames = userConfig
						.getSubsections(((Section) parentElement).name);
				for (String subSectionName : subSectionNames)
					result.add(new SubSection(section, subSectionName));

				Set<String> entryNames = userConfig.getNames(section.name);
				for (String entryName : entryNames) {
					String[] values = userConfig.getStringList(section.name,
							null, entryName);
					if (values.length == 1)
						result
								.add(new Entry(section, entryName, values[0],
										-1));
					else {
						int index = 0;
						for (String value : values)
							result.add(new Entry(section, entryName, value,
									index++));
					}
				}
			}
			if (parentElement instanceof SubSection) {
				SubSection subSection = (SubSection) parentElement;
				Set<String> entryNames = userConfig.getNames(
						subSection.parent.name, subSection.name);
				for (String entryName : entryNames) {
					String[] values = userConfig.getStringList(
							subSection.parent.name, subSection.name, entryName);
					if (values.length == 1)
						result.add(new Entry(subSection, entryName, values[0],
								-1));
					else {
						int index = 0;
						for (String value : values)
							result.add(new Entry(subSection, entryName, value,
									index++));
					}
				}
			}
			return result.toArray();
		}

		public Object getParent(Object element) {
			if (element instanceof Section)
				return null;
			if (element instanceof SubSection)
				return ((SubSection) element).parent;
			if (element instanceof Entry) {
				Entry entry = (Entry) element;
				if (entry.sectionparent != null)
					return entry.sectionparent;
				return entry.subsectionparent;
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			return getChildren(element) != null
					&& getChildren(element).length > 0;
		}
	}

