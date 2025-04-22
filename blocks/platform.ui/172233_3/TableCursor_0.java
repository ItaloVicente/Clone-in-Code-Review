			} else if (((Table)getParent()).getColumnCount() == 0) {
				x += 5;
			} else {
				int alignmnent = ((Table)getParent()).getColumn(cell.getColumnIndex()).getAlignment();
				switch (alignmnent) {
					case SWT.LEFT:
						x += 5;
						break;
					case SWT.RIGHT:
						x = bounds.width- extent.x - 2;
						break;
					case SWT.CENTER:
						x += (bounds.width - x - extent.x) / 2 + 2;
						break;
