			int cat1 = category(e1);
			int cat2 = category(e2);

			if (cat1 != cat2) {
				return cat1 - cat2;
			}

			String name1 = getStagingEntryText(e1);
			String name2 = getStagingEntryText(e2);

			return comparator.compare(name1, name2);
		}

		private String getStagingEntryText(Object element) {
			StagingEntry stagingEntry = getStagingEntry(element);
			if (stagingEntry != null) {
				text = stagingEntry.getParentPath().toString().replace('/',
						'\001');
				if (isFileNamesFirst()) {
					text = '\255' + stagingEntry.getName() + '\001' + text;
				} else {
					text = text + '\255' + stagingEntry.getName();
				}
			} else if (element instanceof StagingFolderEntry) {
				text = ((StagingFolderEntry) element).getNodePath().toString()
						.replace('/', '\001');
			}
			return text;
