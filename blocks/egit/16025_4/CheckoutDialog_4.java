		if (BranchOperationUI.checkoutWillShowQuestionDialog(refName))
			okButton.setText(UIText.CheckoutDialog_OkCheckoutWithQuestion);
		else
			okButton.setText(UIText.CheckoutDialog_OkCheckout);

		okButton.setEnabled(refName != null && !refName.equals(currentBranch));
