		source.getRealm().exec(() -> {
			boolean destinationRealmReached = false;
			final MultiStatus multiStatus = BindingStatus.ok();
			try {
				Object value = source.getValue();

				IStatus status = updateValueStrategy.validateAfterGet(value);
				if (!mergeStatus(multiStatus, status))
					return;

				final Object convertedValue = updateValueStrategy.convert(value);

				status = updateValueStrategy.validateAfterConvert(convertedValue);
				if (!mergeStatus(multiStatus, status))
					return;
				if (policy == UpdateValueStrategy.POLICY_CONVERT && !explicit)
					return;

				status = updateValueStrategy.validateBeforeSet(convertedValue);
				if (!mergeStatus(multiStatus, status))
					return;
				if (validateOnly)
					return;

				destinationRealmReached = true;
				destination.getRealm().exec(() -> {
					if (destination == target) {
						updatingTarget = true;
					} else {
						updatingModel = true;
					}
					try {
						IStatus setterStatus = updateValueStrategy.doSet(destination, convertedValue);

						mergeStatus(multiStatus, setterStatus);
					} finally {
						if (destination == target) {
							updatingTarget = false;
						} else {
							updatingModel = false;
