			String ref = candidateChange.getRefName();
			if (ref != null) {
				refText.setText(ref);
			} else {
				refText.setText(candidateChange.getChangeNumber().toString());
			}
