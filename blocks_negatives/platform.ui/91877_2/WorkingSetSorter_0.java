			 ILabelProvider labelProvider = (ILabelProvider) ((StructuredViewer)viewer).getLabelProvider();
			 String text1 = labelProvider.getText(e1);
			 String text2 = labelProvider.getText(e2);
			 if(text1 != null) {
				 return text1.compareTo(text2);
			 }
