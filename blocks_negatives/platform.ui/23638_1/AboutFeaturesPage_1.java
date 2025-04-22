		return new String[] { info.getProviderName(), info.getName(),
				info.getVersion(), info.getId() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.internal.about.TableListPage#getSelectionValue()
	 */
	protected Collection getSelectionValue() {
		if (table == null || table.isDisposed())
			return null;
		TableItem[] items = table.getSelection();
		if (items.length <= 0) {
			return null;
		}
		ArrayList list = new ArrayList(1);
		list.add(items[0].getData());
		return list;
