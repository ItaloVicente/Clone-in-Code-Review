				MTrimBar bar = modelService.getTrim((MTrimmedWindow) window, SideValue.TOP);

				List<MToolControl> toRemove = new ArrayList<MToolControl>();
				for (MUIElement child : bar.getChildren()) {
					String trimElementId = child.getElementId();
					if (child instanceof MToolControl && trimElementId.contains(perspectiveId)) {
						toRemove.add((MToolControl) child);
