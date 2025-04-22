		} else {
			if (modelsForSource.remove(model)) {
				result = decrementRefCount(model);
				if (modelsForSource.isEmpty()) {
					modelMap.remove(source);
				}
			} else {
				logWarning("Ignored attempt to remove a saveable that was not registered", source, model); //$NON-NLS-1$
