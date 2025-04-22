			if (failures > 0) {
				RepeatedTestException e = new RepeatedTestException(
						MessageFormat.format(
								"Test failed {0} times out of {1} repeated executions"
								Integer.valueOf(failures)
								Integer.valueOf(repetitions)));
				LOG.log(Level.SEVERE
				throw e;
			}
