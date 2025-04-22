				int currentDirection = table.getSortDirection();
				switch (currentDirection) {
				case SWT.NONE:
					reversed = Boolean.FALSE;
					break;
				case SWT.UP:
					reversed = Boolean.TRUE;
					break;
				case SWT.DOWN:
				default:
					reversed = null;
					break;
				}
			} else
				reversed = Boolean.FALSE;

			if (reversed == null) {
				table.setSortColumn(null);
				table.setSortDirection(SWT.NONE);
				filesViewer.setComparator(null);
				return;
