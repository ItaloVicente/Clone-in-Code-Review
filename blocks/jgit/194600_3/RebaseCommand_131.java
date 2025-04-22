			boolean isLast = steps.isEmpty();
			if (!isLast) {
				switch (steps.get(0).getAction()) {
				case FIXUP:
				case SQUASH:
					break;
				default:
					isLast = true;
					break;
				}
			}
