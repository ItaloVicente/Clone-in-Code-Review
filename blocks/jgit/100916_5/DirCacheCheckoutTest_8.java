			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
