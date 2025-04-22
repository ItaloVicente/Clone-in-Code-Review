			ILabelProvider labelProvider = (ILabelProvider) ((StructuredViewer) viewer).getLabelProvider();

			if (labelProvider instanceof DecoratingStyledCellLabelProvider && !DISABLE_FIX_FOR_364735) {
				DecoratingStyledCellLabelProvider dprov = (DecoratingStyledCellLabelProvider) labelProvider;
				IStyledLabelProvider styledLabelProvider = dprov.getStyledStringProvider();
				String text1 = styledLabelProvider.getStyledText(e1).getString();
				String text2 = styledLabelProvider.getStyledText(e2).getString();
				if (text1 != null) {
					return text1.compareTo(text2);
				}
				return -1;
			}

			if (labelProvider instanceof DecoratingLabelProvider && !DISABLE_FIX_FOR_364735) {
				DecoratingLabelProvider dprov = (DecoratingLabelProvider) labelProvider;
				labelProvider = dprov.getLabelProvider();
			}

			String text1 = labelProvider.getText(e1);
			String text2 = labelProvider.getText(e2);
			if (text1 != null) {
				return text1.compareTo(text2);
			}
