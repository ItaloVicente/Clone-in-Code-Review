		FIND: for (ObjectToPack otp : objectsLists[type]) {
			ObjectToPack base = otp.getDeltaBase();
			if (base != null) {
				int d = 0;
				do {
					if (d < base.getChainLength())
						continue FIND;
					base.setChainLength(++d);
					base = base.getDeltaBase();
				} while (base != null && base != otp);
				continue;
			}

