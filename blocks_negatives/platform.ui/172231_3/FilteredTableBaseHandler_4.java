			if (text.isFocusControl()) {
				table.setFocus();
				table.setSelection(index >= 1 ? index - 1 : table.getItemCount() - 1);
			} else if (index == 0) {
				text.setFocus();
			} else {
				table.setSelection(index >= 1 ? index - 1 : table.getItemCount() - 1);
			}
