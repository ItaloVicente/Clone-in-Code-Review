				recentWorkspaces[i] = path;
			}
		} catch (IOException e) {
			return false;
		} catch (WorkbenchException e) {
			return false;
		} finally {
			if (recentWorkspaces == null) {
