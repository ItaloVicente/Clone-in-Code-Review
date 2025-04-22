				if (block) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							showStatusAdapter(statusAdapter, true);
						}
					});

				} else {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							showStatusAdapter(statusAdapter, false);
						}
					});
				}
