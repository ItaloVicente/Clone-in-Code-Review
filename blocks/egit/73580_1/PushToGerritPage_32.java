			Ref currentHead = repository.exactRef(Constants.HEAD);
			String ref = prefixCombo.getItem(prefixCombo.getSelectionIndex())
					+ branchText.getText().trim();
			if (topicText.isEnabled()) {
				ref = setTopicInRef(ref, topicText.getText().trim());
			}
