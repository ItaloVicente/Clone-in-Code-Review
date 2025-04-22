			final StringBuilder builder = new StringBuilder(46);
			if (depth != Transport.DEPTH_INFINITE) {
				builder.append(depth);
				builder.append('\n');
			}
			System.out.println(Thread.currentThread().getName() + ":\t"
					+ "BasePackFetchConnection.sendWants.line='"
					+ line.toString() + "'");
			System.out.println(Thread.currentThread().getName() + ":\t"
					+ "BasePackFetchConnection.sendWants.line='"
					+ builder.toString() + "'");
