				nestedProjectsTable.getControl().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if (nestedProjectsTable.getControl().isDisposed()) {
							return;
						}
						nestedProjectsTable.refresh();
						nestedProjectsTable.getTable().update();
						nestedProjectsTable.getTable().redraw();
