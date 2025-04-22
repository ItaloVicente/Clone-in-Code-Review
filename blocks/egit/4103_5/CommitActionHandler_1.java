		boolean committed = commitUi.commit();

		if (committed
				&& commitUi.isExecutePush())
			executePushCommand(HandlerUtil.getCurrentSelection(event));

