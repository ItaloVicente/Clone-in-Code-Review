				piper.join(10000);
				if (r[0] != null && r[0].length() > 0) {
					return r[0];
				}

			} catch (InterruptedException e) {
				System.err.println("interrupted executing " + command[0]);
			}
			if (readException[0] != null) {
				throw readException[0];
