
		public Object getParent(Object object) {
			return parent;
		}

		public Object[] getChildren(Object o) {
			if (children == null) {
				List<Object> allChildren = new ArrayList<Object>();
				Set<String> subSectionNames = parent.config
						.getSubsections(name);
				for (String subSectionName : subSectionNames)
					allChildren.add(new SubSection(parent.config, this,
							subSectionName));

				Set<String> entryNames = parent.config.getNames(name);
				for (String entryName : entryNames) {
					String[] values = parent.config.getStringList(name, null,
							entryName);
					if (values.length == 1)
						allChildren.add(new Entry(this, entryName, values[0],
								-1));
					else {
						int index = 0;
						for (String value : values)
							allChildren.add(new Entry(this, entryName, value,
									index++));
					}
				}
				return allChildren.toArray();
			}
			return children;
		}

		public String getLabel(Object o) {
			return name;
		}

		public Entry getEntry(String subsectionName, String entryName) {
			if (subsectionName != null) {
				for (Object child : getChildren(this))
					if (child instanceof SubSection
							&& ((SubSection) child).name.equals(subsectionName))
						return ((SubSection) child).getEntry(entryName);
			} else
				for (Object child : getChildren(this))
					if (child instanceof Entry
							&& ((Entry) child).name.equals(entryName))
						return (Entry) child;
			return null;
		}
