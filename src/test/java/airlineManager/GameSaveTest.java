package airlineManager;

public class GameSaveTest {

    private AirlineManagerGame createGame() {

	}
	
	private String getStringRep() {
		return "# name\nTestList\n# entries\nE1\nE2\nE3\nE4\n";
	}
	
	@Test
	public void testWriteToOS() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TodoList tl = getTodoList();
		TodoFileSupport fs = new TodoFileSupport();
		fs.writeTodoList(tl, os);
		
		String actual = new String(os.toByteArray());
		String expected = getStringRep();
		
		assertEquals(expected, actual, "Written string representation is not correct.");
	}
	
	@Test
	public void testReadFromIS() throws UnsupportedEncodingException {
		InputStream is = new ByteArrayInputStream(getStringRep().getBytes("UTF-8"));

		TodoFileSupport fs = new TodoFileSupport();
		TodoList actual = fs.readTodoList(is);
		TodoList expected = getTodoList();
		
		var actualIterator = actual.iterator();
		var expectedIterator = expected.iterator();
		
		int i = 0;
		while (expectedIterator.hasNext()) {
			try {
				assertEquals(expectedIterator.next().getText(), actualIterator.next().getText(), "Element mismatch at position " + i);
			} catch (IndexOutOfBoundsException e) {
				fail("The read list does not contain the correct number of elements.");
			}
			i++;
		}
		assertEquals(expected.getName(), actual.getName(), "Names does not match.");
	}
}
