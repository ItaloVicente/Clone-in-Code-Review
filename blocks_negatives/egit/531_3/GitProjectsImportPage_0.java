		projectsList.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.widget instanceof Tree) {
					TreeItem item = ((Tree) e.widget).getItem(new Point(e.x,
							e.y));
					if (item != null) {
						if (item.getChecked())
							checkedItems.add(item.getData());
						else
							checkedItems.remove(item.getData());
						setPageComplete(!checkedItems.isEmpty());
					}
				}
