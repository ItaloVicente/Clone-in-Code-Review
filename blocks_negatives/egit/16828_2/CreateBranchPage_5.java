			if (layoutChanged)
				upstreamConfigGroup.getParent().layout(true);

			if (!gd.exclude)
				buttonConfigMerge.setSelection(false);
			buttonConfigRebase.setSelection(false);
			buttonConfigNone.setSelection(false);
			switch (upstreamConfig) {
			case MERGE:
				buttonConfigMerge.setSelection(true);
				break;
			case REBASE:
				buttonConfigRebase.setSelection(true);
				break;
			case NONE:
				buttonConfigNone.setSelection(true);
				break;
			}
