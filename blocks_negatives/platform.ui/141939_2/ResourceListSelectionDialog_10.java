        boolean result = super.close();
        labelProvider.dispose();
        return result;
    }

    /**
     * @see org.eclipse.jface.window.Window#create()
     */
    @Override
