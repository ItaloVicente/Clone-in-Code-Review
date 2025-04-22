			@Override
			public void widgetSelected(SelectionEvent e) {
				if (ignoreTabSelChanges)
					return;

				MUIElement ele = (MUIElement) e.item.getData(OWNING_ME);
				ele.getParent().setSelectedElement(ele);
				if (ele instanceof MPlaceholder)
					ele = ((MPlaceholder) ele).getRef();
				if (ele instanceof MPart)
					activate((MPart) ele);
			}
		});
