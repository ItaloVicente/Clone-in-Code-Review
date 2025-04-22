			GridLayoutFactory.fillDefaults().applyTo(mergeResultGroup);
			switch (status) {
			case OK:
			case FAST_FORWARD:
			case UP_TO_DATE:
			case FAILED:
			case ABORTED:
				break;
			case STOPPED:
