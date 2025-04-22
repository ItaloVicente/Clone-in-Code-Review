					return manageChanges;
				}
			};
			windowContext.runAndTrack(menuChangeManager);
		}

		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, windowWidgetHandler);
