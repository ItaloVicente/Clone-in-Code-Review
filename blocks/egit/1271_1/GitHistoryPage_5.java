
			@Override
			public void run() {
				super.run();
				wrapCommentAction.setEnabled(isChecked());
				fillCommentAction.setEnabled(isChecked());
			}

