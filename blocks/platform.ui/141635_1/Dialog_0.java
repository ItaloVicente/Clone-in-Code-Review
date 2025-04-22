		Button button = WidgetFactory.button(SWT.PUSH).//
				text(label).//
				font(JFaceResources.getDialogFont()).//
				data(Integer.valueOf(id)).//
				onSelect(event -> buttonPressed(((Integer) event.widget.getData()).intValue())).//
				create(parent);
