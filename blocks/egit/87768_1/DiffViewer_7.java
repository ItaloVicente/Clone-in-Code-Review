				try {
					switch (next.getType()) {
					case CONTEXT:
						int nofLines = document.getNumberOfLines(
								next.getOffset(), next.getLength());
						return nofLines - 1;
					case ADD:
					case REMOVE:
