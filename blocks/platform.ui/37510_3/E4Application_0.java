				boolean match = ignoreCase ? fullArgName.equalsIgnoreCase(args[i]) : fullArgName.equals(args[i]);
				if (match) {
					if (i + 1 < args.length) {
						return args[i + 1];
					}
					return optionalSecondArg ? "" : null;
				}
