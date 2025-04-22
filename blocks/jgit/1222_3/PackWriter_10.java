
		while (0 < cnt && list[cnt - 1].isDoNotDelta())
			cnt--;
		if (cnt == 0)
			return;

		monitor.beginTask(JGitText.get().compressingObjects
