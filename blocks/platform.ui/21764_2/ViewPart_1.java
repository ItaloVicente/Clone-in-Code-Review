
    @Override
    public void dispose() {
        if (page != null) {
            page.removePartListener(partListener);
        }
        super.dispose();
    }
