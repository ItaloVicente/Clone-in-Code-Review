				if (o instanceof MPartDescriptor) {
					String description = ((MPartDescriptor) o).getTooltip();
					description = LocalizationHelper.getLocalized(description, (MPartDescriptor) o,
							context);
					if (description != null && description.length() == 0)
						description = WorkbenchSWTMessages.ShowView_noDesc;
