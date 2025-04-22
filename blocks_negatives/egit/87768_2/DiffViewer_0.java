				switch (next.diffType) {
				case ADD:
				case REMOVE:
					try {
						int diffLine = document
								.getLineOfOffset(next.getOffset());
