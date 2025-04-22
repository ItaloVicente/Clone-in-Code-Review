		userConfig = SystemReader.getInstance().openUserConfig(FS.DETECTED);
	}

	private final static class Section {
		private final String name;

		private final Config config;

		Section(Config config, String name) {
			this.config = config;
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + name.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Section other = (Section) obj;
			if (!name.equals(other.name))
				return false;
			return true;
		}
	}

	private final static class SubSection {

		private final Section parent;

		private final String name;

		SubSection(Section parent, String name) {
			this.parent = parent;
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + name.hashCode();
			result = prime * result + parent.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SubSection other = (SubSection) obj;
			if (!name.equals(other.name))
				return false;
			if (!parent.equals(other.parent))
				return false;
			return true;
		}
	}

	private final static class Entry {

		private final Section sectionparent;

		private final SubSection subsectionparent;

		private final String name;

		private final String value;

		private final int index;

		Entry(Section parent, String name, String value, int index) {
			this.sectionparent = parent;
			this.subsectionparent = null;
			this.name = name;
			this.value = value;
			this.index = index;
		}

		public void addValue(String newValue) {
			if (newValue.length() == 0)
				throw new IllegalArgumentException(
						UIText.GlobalConfigurationPreferencePage_EmptyStringNotAllowed);
			Config config = getConfig();

			List<String> entries;
			if (sectionparent != null) {
				entries = new ArrayList<String>(Arrays.asList(config
						.getStringList(sectionparent.name, null, name)));
				entries.add(Math.max(index, 0), newValue);
				config.setStringList(sectionparent.name, null, name, entries);
			} else {
				entries = new ArrayList<String>(Arrays.asList(config
						.getStringList(subsectionparent.parent.name,
								subsectionparent.name, name)));
				entries.add(Math.max(index, 0), newValue);
				config.setStringList(subsectionparent.parent.name,
						subsectionparent.name, name, entries);
			}

		}

		Entry(SubSection parent, String name, String value, int index) {
			this.sectionparent = null;
			this.subsectionparent = parent;
			this.name = name;
			this.value = value;
			this.index = index;
		}

		public void changeValue(String newValue)
				throws IllegalArgumentException {
			if (newValue.length() == 0)
				throw new IllegalArgumentException(
						UIText.GlobalConfigurationPreferencePage_EmptyStringNotAllowed);
			Config config = getConfig();

			if (index < 0) {
				if (sectionparent != null)
					config.setString(sectionparent.name, null, name, newValue);
				else
					config.setString(subsectionparent.parent.name,
							subsectionparent.name, name, newValue);
			} else {
				String[] entries;
				if (sectionparent != null) {
					entries = config.getStringList(sectionparent.name, null,
							name);
					entries[index] = newValue;
					config.setStringList(sectionparent.name, null, name, Arrays
							.asList(entries));
				} else {
					entries = config.getStringList(
							subsectionparent.parent.name,
							subsectionparent.name, name);
					entries[index] = newValue;
					config.setStringList(subsectionparent.parent.name,
							subsectionparent.name, name, Arrays.asList(entries));
				}
			}
		}

		private Config getConfig() {
			Config config;
			if (sectionparent != null)
				config = sectionparent.config;
			else
				config = subsectionparent.parent.config;
			return config;
		}

		public void removeValue() {
			Config config = getConfig();

			if (index < 0) {
				if (sectionparent != null)
					config.unset(sectionparent.name, null, name);
				else
					config.unset(subsectionparent.parent.name,
							subsectionparent.name, name);
			} else {
				List<String> entries;
				if (sectionparent != null) {
					entries = new ArrayList<String>(Arrays.asList(config
							.getStringList(sectionparent.name, null, name)));

					entries.remove(index);
					config.setStringList(sectionparent.name, null, name,
							entries);
				} else {
					entries = new ArrayList<String>(Arrays.asList(config
							.getStringList(subsectionparent.parent.name,
									subsectionparent.name, name)));
					entries.remove(index);
					config.setStringList(subsectionparent.parent.name,
							subsectionparent.name, name, entries);
				}
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index;
			result = prime * result + name.hashCode();
			result = prime * result
					+ ((sectionparent == null) ? 0 : sectionparent.hashCode());
			result = prime
					* result
					+ ((subsectionparent == null) ? 0 : subsectionparent
							.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (index != other.index && (index > 0 || other.index > 0))
				return false;
			if (!name.equals(other.name))
				return false;
			if (sectionparent == null) {
				if (other.sectionparent != null)
					return false;
			} else if (!sectionparent.equals(other.sectionparent))
				return false;
			if (subsectionparent == null) {
				if (other.subsectionparent != null)
					return false;
			} else if (!subsectionparent.equals(other.subsectionparent))
				return false;
			return true;
		}
	}

	private static final class ContentProvider implements ITreeContentProvider {
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
						result.add(new Entry(section, entryName, values[0], -1));
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

	private static final class LabelProvider extends BaseLabelProvider
			implements ITableLabelProvider, IFontProvider {
		private Font boldFont = null;

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				if (element instanceof Section)
					return ((Section) element).name;
				if (element instanceof SubSection)
					return ((SubSection) element).name;
				if (element instanceof Entry) {
					Entry entry = (Entry) element;
					if (entry.index < 0)
						return entry.name;
				}
				return null;
			case 1:
				if (element instanceof Entry)
					return ((Entry) element).value;
				return null;
			default:
				return null;
			}
		}

		public Font getFont(Object element) {
			if (element instanceof Section || element instanceof SubSection)
				return getBoldFont();
			else
				return null;
		}

		private Font getBoldFont() {
			if (boldFont != null)
				return boldFont;
			Font defaultFont = JFaceResources.getDefaultFont();
			FontData[] data = defaultFont.getFontData();
			for (int i = 0; i < data.length; i++)
				data[i].setStyle(SWT.BOLD);

			boldFont = new Font(Display.getDefault(), data);
			return boldFont;
		}
