				ExternalActionManager.ICallback callback = ExternalActionManager.getInstance().getCallback();
				String commandId = action.getActionDefinitionId();
				if ((callback != null) && (commandId != null) && (toolTip != null)) {
					String acceleratorText = callback.getAcceleratorText(commandId);
					if (acceleratorText != null && acceleratorText.length() != 0) {
						toolTip = JFaceResources.format("Toolbar_Tooltip_Accelerator", //$NON-NLS-1$
								toolTip, acceleratorText);
