		if (item instanceof StagingEntry) {
			int w = ((StagingEntry) item).getExtraWidth();
			if (w > 0) {
				Rectangle bounds = ((TreeViewer) viewer).getTree()
						.getClientArea();
				if (event.x >= bounds.x + bounds.width - w) {
					return false;
				}
			}
		}
