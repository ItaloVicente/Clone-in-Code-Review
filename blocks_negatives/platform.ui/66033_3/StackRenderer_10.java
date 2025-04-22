				if (pStack != null)
					styleElement(pStack, true);
				styleElement(newActivePart, true);
			}
		};
		eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, stylingHandler);
