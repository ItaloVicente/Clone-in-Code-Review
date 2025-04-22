						new IInputValidator() {
							@Override
							public String isValid(String newText) {
								try {
									Integer.valueOf(newText);
									return null;
								} catch (NumberFormatException e) {
									return "Enter a number between "
											+ Integer.MIN_VALUE + " and "
											+ Integer.MAX_VALUE;
								}
