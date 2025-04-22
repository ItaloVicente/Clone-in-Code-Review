			if (minimalNegotiationSet != null) {
				Ref current = local.exactRef(r.getName());
				if (current != null) {
					ObjectId o = current.getObjectId();
					if (o != null && !o.equals(ObjectId.zeroId())) {
						minimalNegotiationSet.add(o);
					}
				}
			}
