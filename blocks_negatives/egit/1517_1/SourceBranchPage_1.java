		for (final Ref r : availableRefs) {
			String n = r.getName();
			if (n.startsWith(Constants.R_HEADS))
				n = n.substring(Constants.R_HEADS.length());
			final TableItem ti = new TableItem(refsTable, SWT.NONE);
			ti.setText(n);
			ti.setChecked(true);
			selectedRefs.add(r);
		}
