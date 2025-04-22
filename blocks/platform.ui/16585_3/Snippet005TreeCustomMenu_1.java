	private class MyContentProvider implements
			ITreeContentProvider<MyModel, MyModel> {

		public MyModel[] getElements(MyModel inputElement) {
			MyModel[] myModels = new MyModel[inputElement.child.size()];
			return inputElement.child.toArray(myModels);
