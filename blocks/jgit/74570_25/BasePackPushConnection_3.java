			System.out.println(
					"BasePackPushConnection: pushOptions = " + pushOptions);
			System.out.println("BasePackPushConnection: capablePushOptions = "
					+ capablePushOptions);
			if (pushOptions != null && capablePushOptions)
				transmitOptions();
