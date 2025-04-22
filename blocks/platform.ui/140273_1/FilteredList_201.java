				final TableItem item = (currentIndex < itemCount) ? fTable.getItem(currentIndex)
						: new TableItem(fTable, SWT.NONE);
				final Label label = fLabels[fFilteredIndices[fFoldedIndices[currentIndex]]];
				item.setText(label.string);
				item.setImage(label.image);
				currentIndex++;
			}
			if (monitor.isCanceled()) {
