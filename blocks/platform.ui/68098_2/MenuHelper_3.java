			if (data.tooltip != null) {
				toolItem.setTooltip(data.tooltip);
			} else if (data.label != null) {
				toolItem.setTooltip(data.label);
			} else {
				toolItem.setTooltip(command.getDescription());
			}
