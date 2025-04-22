		} catch (IOException e) {
			c.unlock();
			throw e;
		} catch (RuntimeException e) {
			c.unlock();
			throw e;
		} catch (Error e) {
