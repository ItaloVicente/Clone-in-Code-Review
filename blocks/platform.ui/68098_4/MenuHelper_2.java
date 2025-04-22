			if (data.icon != null) {
				iconURI = getIconURI(data.icon, application.getContext());
			}
			if (iconURI == null) {
				iconURI = getIconURI(command.getElementId(), application.getContext(),
						ICommandImageService.TYPE_DEFAULT);
			}
			if (iconURI == null) {
				toolItem.setLabel(command.getCommandName());
			} else {
				toolItem.setIconURI(iconURI);
			}
