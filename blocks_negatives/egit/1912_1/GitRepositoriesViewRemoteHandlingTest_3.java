		final SWTBotTreeItem errorItem = remotesItem.getNode("test3")
				.getNode(0);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Object data = errorItem.widget.getData();
				assertTrue(data instanceof ErrorNode);
			}
		});
