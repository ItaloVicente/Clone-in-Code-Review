        pattern.addModifyListener(new ModifyListener() {
            @Override
			public void modifyText(ModifyEvent e) {
                refresh(false);
            }
        });
