			if (e.button == 1) {
				if (table.equals(e.getSource())) {
					Object o = table.getItem(new Point(e.x, e.y));
					TableItem selection = table.getSelection()[0];
					if (selection.equals(o)) {
						gotoSelectedElement();
