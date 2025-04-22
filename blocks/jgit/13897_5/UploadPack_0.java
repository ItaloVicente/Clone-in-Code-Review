		if (!biDirectionalPipe) {
			if (requestPolicy == RequestPolicy.ADVERTISED)
				requestPolicy = RequestPolicy.REACHABLE_COMMIT;
			else if (requestPolicy == RequestPolicy.TIP)
				requestPolicy = RequestPolicy.REACHABLE_COMMIT_TIP;
		}
