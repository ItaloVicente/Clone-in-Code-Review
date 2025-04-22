				if (otp.isDeltaRepresentation())
					writeDeltaObjectHeader(otp, reuse);
				else
					writeObjectHeader(otp.getType(), reuse.getSize());
				reuse.copyRawData(out, buf, windowCursor);
			} finally {
				reuse.endCopyRawData();
