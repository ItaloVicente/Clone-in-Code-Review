
				@Override
				public void setChecked(boolean checked) {
					super.setChecked(checked);
					int accelerator = getAccelerator();
					if (checked) {
						setToolTipText(
								NLS.bind(UIText.GitHistoryPage_FindHideTooltip,
										formatAccelerator(accelerator)));
					} else {
						setToolTipText(
								NLS.bind(UIText.GitHistoryPage_FindShowTooltip,
										formatAccelerator(accelerator)));
					}
				}

