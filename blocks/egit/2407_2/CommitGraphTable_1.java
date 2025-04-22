		if (c instanceof PlotCommit) {
			table.setSelection(new StructuredSelection(c));
			table.reveal(c);
		} else {
			PlotCommit swtCommit = commitsMap.get(c.getId().name());
			table.setSelection(new StructuredSelection(swtCommit));
			table.reveal(swtCommit);
		}
