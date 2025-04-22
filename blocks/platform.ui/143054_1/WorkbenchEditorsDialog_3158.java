	public WorkbenchEditorsDialog(IWorkbenchWindow window) {
		super(window.getShell());
		this.window = window;
		setTitle(WorkbenchMessages.WorkbenchEditorsDialog_title);

		IDialogSettings s = getDialogSettings();
		if (s.get(ALLPERSP) == null) {
			sortColumn = 0;
		} else {
			showAllPersp = s.getBoolean(ALLPERSP);
			sortColumn = s.getInt(SORT);
			String[] array = s.getArray(BOUNDS);
			if (array != null) {
				bounds = new Rectangle(0, 0, 0, 0);
				bounds.x = Integer.valueOf(array[0]).intValue();
				bounds.y = Integer.valueOf(array[1]).intValue();
				bounds.width = Integer.valueOf(array[2]).intValue();
				bounds.height = Integer.valueOf(array[3]).intValue();
			}
			array = s.getArray(COLUMNS);
			if (array != null) {
				columnsWidth = new int[array.length];
				for (int i = 0; i < columnsWidth.length; i++) {
