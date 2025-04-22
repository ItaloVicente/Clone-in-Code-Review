			if (control instanceof CTabFolder) {
				CTabFolder folder = (CTabFolder) control;
				if (folder.getSelectionBackground().isDisposed()) {
					folder.setSelectionBackground((Color) null);
				}
			}
