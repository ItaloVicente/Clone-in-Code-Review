				if (activeStack != null) {
					activeStack.getChildren().add(providedPart);
				} else {
					List<MPartStack> sharedStacks = modelService.findElements(area, null, MPartStack.class);
					if (sharedStacks.size() > 0) {
						for (MPartStack stack : sharedStacks) {
							if (stack.isToBeRendered()) {
								stack.getChildren().add(providedPart);
								break;
