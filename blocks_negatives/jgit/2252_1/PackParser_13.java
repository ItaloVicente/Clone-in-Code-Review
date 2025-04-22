		} catch (IOException err) {
			if (dstPack != null)
				FileUtils.delete(dstPack);
			if (dstIdx != null)
				FileUtils.delete(dstIdx);
			throw err;
