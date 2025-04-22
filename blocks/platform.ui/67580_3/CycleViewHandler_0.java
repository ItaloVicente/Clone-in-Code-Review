		parts.stream().sorted((firstPart, secondPart) -> {
			Long firstPartActivationTime = (Long) firstPart.getTransientData()
					.getOrDefault(PartServiceImpl.PART_ACTIVATION_TIME, Long.MIN_VALUE);
			Long secondPartActivationTime = (Long) secondPart.getTransientData()
					.getOrDefault(PartServiceImpl.PART_ACTIVATION_TIME, Long.MIN_VALUE);
			return -(firstPartActivationTime.compareTo(secondPartActivationTime));
		}).forEach(part -> {
