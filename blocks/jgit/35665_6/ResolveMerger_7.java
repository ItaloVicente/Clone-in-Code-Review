			if (tw.canGetAttributes()) {
				Set<Attribute> attrs = tw
						.getAttributes(OperationType.CHECKOUT_OP);
				if (dce != null && hasIdentSet(attrs)) {
					toBeCheckedOut.put(tw.getPathString()
					toBeCheckedOutAttributes.put(tw.getPathString()
							tw.getAttributes(OperationType.CHECKOUT_OP));
				}
			}

