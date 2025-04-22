			if (depth > 0 && depth < Transport.DEPTH_INFINITE) {
				final StringBuilder builder = new StringBuilder(46);
				builder.append(depth);
				builder.append('\n');
				System.out.println(Thread.currentThread().getName() + ":\t"
						+ "BasePackFetchConnection.sendWants.deepen='"
						+ builder.toString() + "'");
				p.writeString(builder.toString());
			}
