			ObjectToPack otp;

			while (restartedProgress != 0
					&& (otp = itr.nextObjectToPack()) != null) {
				if (objectsMap.addIfAbsent(otp) == otp) {
					otp.setPathHash(itr.getPathHashCode());
					objectsLists[otp.getType()].add(otp);
					if (restartedProgress != 0)
						restartedProgress--;
					else
						countingMonitor.update(1);
				}
