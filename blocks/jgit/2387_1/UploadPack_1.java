			packOut.flush();

			if (msgOut != null) {
				String msg = pw.getStatistics().getMessage() + '\n';
				msgOut.write(Constants.encode(msg));
				msgOut.flush();
			}

