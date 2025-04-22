
		public Object[] getChildren(Object o) {
			if (children == null) {
				List<Entry> entries = new ArrayList<Entry>();
				Set<String> entryNames = config.getNames(parent.name, name);
				for (String entryName : entryNames) {
					String[] values = config.getStringList(parent.name, name,
							entryName);
					if (values.length == 1)
						entries.add(new Entry(this, entryName, values[0], -1));
					else {
						int index = 0;
						for (String value : values)
							entries.add(new Entry(this, entryName, value,
									index++));
					}
				}
				children = entries.toArray(new Entry[entries.size()]);
			}
			return children;
		}

		public String getLabel(Object o) {
			return name;
		}

		public Object getParent(Object object) {
			return parent;
		}

		public Entry getEntry(String entryName) {
			for (Object child : getChildren(this))
				if (entryName.equals(((Entry) child).name))
					return (Entry) child;
			return null;
		}
