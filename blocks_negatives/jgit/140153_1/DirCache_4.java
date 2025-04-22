		} catch (IOException err) {
			tmp.unlock();
			throw err;
		} catch (RuntimeException err) {
			tmp.unlock();
			throw err;
		} catch (Error err) {
