			Object[] values = propEntry.getValues();
			if (values.length == 1 && !(values[0] instanceof PropertySheetAuto.Car)) {
				values = new Object[0];
			}
			assertArrayEquals(structuredSelection.toArray(), values);
		}
