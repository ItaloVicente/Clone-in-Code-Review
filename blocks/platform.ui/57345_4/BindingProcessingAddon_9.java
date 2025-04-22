						MKeyBinding oldBinding4 = (MKeyBinding) EcoreUtil
								.copy((EObject) binding4);
						if (UIEvents.EventTypes.REMOVE_MANY.equals(event
								.getProperty(UIEvents.EventTags.TYPE))) {
							@SuppressWarnings("unchecked")
							Collection<MParameter> parms = (Collection<MParameter>) oldObj3;
							oldBinding4.getParameters().addAll(parms);
						} else {
							oldBinding4.getParameters().add(
									(MParameter) oldObj3);
