			Shell shell = getShell();
			Composite parent = shell.getParent();
			if (parent == null) {
				return "";
			}
			StringBuilder sb = new StringBuilder();
			do {
				String id = WidgetElement.getID(parent);
				if (id != null && id.length() > 0) {
					sb.append(id).append(' ');
