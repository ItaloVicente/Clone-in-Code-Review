			}

			@Override
			public void refresh(String filter) {
				super.refresh(filter);
				if (isLoadingPreviousElements) {
					showHintText(QuickAccessMessages.QuickAccessContents_RestoringPreviousChoicesLabel, null);
