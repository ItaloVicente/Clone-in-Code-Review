			boolean isIncluded = !isDeleted
					&& includes(sourceModel.getPosition(annotation),
							getTextWidget().getCaretOffset());
			boolean isFixable = isIncluded && sourceViewer
					.getQuickAssistAssistant().canFix(annotation);
			if (isFixable) {
