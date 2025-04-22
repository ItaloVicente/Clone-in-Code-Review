        selectionListener = widgetSelectedAdapter(event -> {
		    Widget widget = event.widget;
		    if (widget == addButton) {
		        addPressed();
		    } else if (widget == removeButton) {
		        removePressed();
		    } else if (widget == upButton) {
		        upPressed();
		    } else if (widget == downButton) {
		        downPressed();
		    } else if (widget == list) {
		        selectionChanged();
		    }
		});
