			}
			if (cmd.errw != null) {
				cmd.errw.flush();
			}
			if (seenHelp) {
				return errs.toByteArray();
			} else if (errs.size() > 0) {
				System.err.print(errs.toString());
			}
