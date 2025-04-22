			assertEquals(
					"Parameter replaceDescriptor should be the same as parameter new Descriptor",
					replacementDescriptor, newDescriptor);
			assertFalse(
					"Parameter replaceDescriptor should not be equals to htmlDescriptor",
					replacementDescriptor.equals(htmlDescriptor));
