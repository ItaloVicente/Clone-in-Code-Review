			MKeyBinding oldBinding = (MKeyBinding) EcoreUtil.copy((EObject) binding);
			if (UIEvents.EventTypes.ADD_MANY.equals(event.getProperty(UIEvents.EventTags.TYPE))) {
				oldBinding.getParameters().removeAll((Collection<?>) newObj);
			} else {
				oldBinding.getParameters().remove(newObj);
			}
			updateBinding(oldBinding, false, ((EObject) binding).eContainer());
			updateBinding(binding, true, null);
		} else if (UIEvents.isREMOVE(event)) {
			Object oldObj = event.getProperty(UIEvents.EventTags.OLD_VALUE);
			MKeyBinding oldBinding = (MKeyBinding) EcoreUtil.copy((EObject) binding);
			if (UIEvents.EventTypes.REMOVE_MANY.equals(event.getProperty(UIEvents.EventTags.TYPE))) {
				@SuppressWarnings("unchecked")
				Collection<MParameter> parms = (Collection<MParameter>) oldObj;
				oldBinding.getParameters().addAll(parms);
			} else {
				oldBinding.getParameters().add((MParameter) oldObj);
