		} else if (UIEvents.isREMOVE(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				if (!(o instanceof MUIElement))
					continue;
				MUIElement element = (MUIElement) o;
				List<MPart> removedParts = modelService.findElements(element, null, MPart.class, null);
				for (MPart part : removedParts) {
					activationList.remove(part);
				}
			}
