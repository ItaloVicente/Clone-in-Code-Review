					} else if (UIEvents.isREMOVE(event)) {
						for (Object oldObj : UIEvents.asIterable(event,
								UIEvents.EventTags.OLD_VALUE)) {
							if (oldObj instanceof MBindingContext) {
								MBindingContext oldCtx = (MBindingContext) oldObj;
								undefineContext(oldCtx);
							}
