				reuseSupport.copyObjectAsIs(out
				otp.setCRC(out.getCRC32());
				writeMonitor.update(1);
				return;
			} catch (StoredObjectRepresentationNotAvailableException gone) {
				if (otp.getOffset() == out.length()) {
					redoSearchForReuse(otp);
					continue;
				} else {
					CorruptObjectException coe;
					coe = new CorruptObjectException(otp
					coe.initCause(gone);
					throw coe;
				}
