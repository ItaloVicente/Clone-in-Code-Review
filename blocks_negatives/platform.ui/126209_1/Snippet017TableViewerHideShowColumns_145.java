			column.getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					column.setData("restoredWidth", Integer.valueOf(width));
				}
			});
