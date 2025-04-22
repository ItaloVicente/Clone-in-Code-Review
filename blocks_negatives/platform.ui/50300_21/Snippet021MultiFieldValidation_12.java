				Integer field1 = (Integer) middleField1.getValue();
				Integer field2 = (Integer) middleField2.getValue();
				if (Math.abs(field1.intValue()) % 2 != Math.abs(field2
						.intValue()) % 2)
					return ValidationStatus
							.error("Fields 1 and 2 must be both even or both odd");
