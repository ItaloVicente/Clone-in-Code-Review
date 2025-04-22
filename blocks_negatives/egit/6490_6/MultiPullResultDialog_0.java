			Entry<Repository, Object> item = (Entry<Repository, Object>) sel
					.getFirstElement();
			if (item.getValue() instanceof PullResult) {
				PullResultDialog dialog = new PullResultDialog(getShell(), item
						.getKey(), (PullResult) item.getValue());
				dialog.open();
