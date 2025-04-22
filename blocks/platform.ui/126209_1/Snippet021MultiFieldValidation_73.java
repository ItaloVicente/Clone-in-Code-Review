						newText -> {
							try {
								Integer.valueOf(newText);
								return null;
							} catch (NumberFormatException e1) {
								return "Enter a number between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE;
