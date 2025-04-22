			for (Entry<Repository, Object> item : (List<Entry<Repository, Object>>) sel
					.toList()) {
				if (item.getValue() instanceof PullResult) {
					PullResultDialog dialog = new PullResultDialog(getShell(),
							item.getKey(), (PullResult) item.getValue());
					dialog.open();
				}
