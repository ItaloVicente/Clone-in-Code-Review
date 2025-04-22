					addPerspectiveItem(added);
				}
			} else if (UIEvents.isREMOVE(event)) {
				for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
					MPerspective removed = (MPerspective) o;
					if (!removed.isToBeRendered())
						continue;

					removePerspectiveItem(removed);
				}
			}
