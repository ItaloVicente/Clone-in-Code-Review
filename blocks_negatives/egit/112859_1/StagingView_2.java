		asyncExec(new Runnable() {
			@Override
			public void run() {
				if (isDisposed())
					return;
				showControl(rebaseSection, isRebasing);
				rebaseSection.getParent().layout(true);
			}
