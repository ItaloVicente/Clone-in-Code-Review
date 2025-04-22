			throw ioe;
		} catch (RuntimeException re) {
			openFail(true);
			throw re;
		} catch (Error re) {
			openFail(true);
			throw re;
