
	private class MyContentProvider implements IStructuredContentProvider<MyModel,List<MyModel>> {

		public MyModel[] getElements(List<MyModel> inputElement) {
			MyModel[] myModels = new MyModel[inputElement.size()];
			return inputElement.toArray(myModels);
