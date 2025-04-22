		} catch (Error e) {
			f.delete();
			throw e;
		} catch (RuntimeException e) {
			f.delete();
			throw e;
		} catch (IOException e) {
