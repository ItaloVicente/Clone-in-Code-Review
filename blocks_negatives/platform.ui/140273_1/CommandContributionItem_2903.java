			ICommandImageService service = locator
					.getService(ICommandImageService.class);
			icon = service.getImageDescriptor(command.getId(),
					ICommandImageService.TYPE_DEFAULT, iconStyle);
			disabledIcon = service.getImageDescriptor(command.getId(),
					ICommandImageService.TYPE_DISABLED, iconStyle);
			hoverIcon = service.getImageDescriptor(command.getId(),
					ICommandImageService.TYPE_HOVER, iconStyle);
