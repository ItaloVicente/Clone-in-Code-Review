							try {
								Type methodType = Type.valueOf(valuePart[0].toUpperCase());
								if (authentication.getType().compareTo(methodType) >= 0) {
									continue;
								}

