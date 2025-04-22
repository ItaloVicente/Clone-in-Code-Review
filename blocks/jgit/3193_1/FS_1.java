
			} catch (InterruptedException e) {
				System.err.println("interrupted executing " + command[0]);
			}
			if (readException[0] != null) {
				throw readException[0];
