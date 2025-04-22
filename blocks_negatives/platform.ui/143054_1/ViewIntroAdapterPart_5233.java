    	setBarVisibility(true);
        super.dispose();
        getSite().getWorkbenchWindow().getWorkbench().getIntroManager()
                .closeIntro(introPart);
        introPart.dispose();
    }
