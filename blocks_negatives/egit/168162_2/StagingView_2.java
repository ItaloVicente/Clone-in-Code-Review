			int cat1 = category(e1);
			int cat2 = category(e2);

			if (cat1 != cat2) {
				return cat1 - cat2;
			}

			String name1 = getStagingObjectText(getStagingObject(e1));
			String name2 = getStagingObjectText(getStagingObject(e2));

			return comparator.compare(name1, name2);
		}

		private String getStagingObjectText(Object stagingObject) {
			if (stagingObject instanceof StagingEntry) {
				StagingEntry stagingEntry = (StagingEntry) stagingObject;
				if (isFileNamesFirst()) {
					text = stagingEntry.getName() + '\001' + stagingEntry
							.getParentPath().toString().replace('/', '\001');
				} else {
					text = stagingEntry.getPath().toString().replace('/',
							'\001');
				}
			} else if (stagingObject instanceof StagingFolderEntry) {
				text = ((StagingFolderEntry) stagingObject).getNodePath()
						.toString().replace('/', '\001');
			}
			return text;
