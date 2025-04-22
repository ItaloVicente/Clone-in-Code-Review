
			if ("transparent".equals(value.getCssText())) {
				if ((widget instanceof Button) || (widget instanceof Label)) {
					Control control = (Control) widget;
					control.setBackground(null);
					control.setBackgroundImage(null);
				}

				return;
			}

