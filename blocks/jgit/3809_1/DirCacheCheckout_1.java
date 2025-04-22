			if (!conflicts.isEmpty()) {
				if (failOnConflict) {
					dc.unlock();
					throw new CheckoutConflictException(
							conflicts.toArray(new String[conflicts.size()]));
				} else
					cleanUpConflicts();
