		return UIUtils.addContentProposalToText(textField, () -> {
			try {
				return getRefsForContentAssist(textField.getText());
			} catch (InvocationTargetException e) {
				Activator.handleError(e.getMessage(), e, true);
				return null;
			} catch (InterruptedException e) {
				return null;
