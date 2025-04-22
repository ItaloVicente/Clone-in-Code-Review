		} catch (IOException e) {
			close(pack);
			throw e;
		} catch (RuntimeException e) {
			close(pack);
			throw e;
		} catch (Error e) {
