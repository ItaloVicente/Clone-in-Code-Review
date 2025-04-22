        linkTargetField.addModifyListener(new ModifyListener() {
            @Override
			public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });
