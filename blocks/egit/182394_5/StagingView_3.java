			Tree tree = viewer.getTree();
			tree.addListener(SWT.MeasureItem, event -> {
				Object obj = ((TreeItem) event.item).getData();
				if (obj instanceof StagingEntry) {
					StagingEntry entry = (StagingEntry) obj;
					String text = getConflictText(entry);
					if (text.isEmpty()) {
						entry.setExtraWidth(0);
						return;
