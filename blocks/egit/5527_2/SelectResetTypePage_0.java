		if (currentRef.equals(targetRef))
			setMessage(NLS.bind(UIText.SelectResetTypePage_PageMessage,
					new String[] { currentRef, "HEAD", targetCommit })); //$NON-NLS-1$
		else
			setMessage(NLS.bind(UIText.SelectResetTypePage_PageMessage,
					new String[] { currentRef, targetRef, targetCommit }));
