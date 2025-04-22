				Duration fsResolution = Duration.between(t1.toInstant()
				Duration clockResolution = measureClockResolution();
				fsResolution = fsResolution.plus(clockResolution);
				LOG.debug("{}: end measure timestamp resolution {} in {}"
						Thread.currentThread()
				return Optional.of(fsResolution);
