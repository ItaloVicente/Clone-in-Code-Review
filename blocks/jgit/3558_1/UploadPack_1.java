			if (options.contains(OPTION_MULTI_ACK_DETAILED)) {
				multiAck = MultiAck.DETAILED;
				noDone = options.contains(OPTION_NO_DONE);
			} else if (options.contains(OPTION_MULTI_ACK))
				multiAck = MultiAck.CONTINUE;
			else
				multiAck = MultiAck.OFF;

			sendPack = negotiate();
		} catch (PackProtocolException err) {
			reportErrorDuringNegotiate(err.getMessage());
			throw err;

		} catch (UploadPackMayNotContinueException err) {
			if (!err.isOutput() && err.getMessage() != null) {
				try {
					pckOut.writeString("ERR " + err.getMessage() + "\n");
					err.setOutput();
				} catch (Throwable err2) {
				}
			}
			throw err;

		} catch (IOException err) {
			reportErrorDuringNegotiate(JGitText.get().internalServerError);
			throw err;
		} catch (RuntimeException err) {
			reportErrorDuringNegotiate(JGitText.get().internalServerError);
			throw err;
		} catch (Error err) {
			reportErrorDuringNegotiate(JGitText.get().internalServerError);
			throw err;
		}
