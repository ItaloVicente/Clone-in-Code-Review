				BindingElement activeBinding = (BindingElement) keyController.getBindingModel()
						.getSelectedElement();
				if (activeBinding != null) {
					activeBinding.setTrigger(keySequence);
				}
				fBindingText.setSelection(fBindingText.getTextLimit());
}
});
