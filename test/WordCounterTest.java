import static org.junit.Assert.*;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.stack.Stack;
import components.stack.Stack1L;


public class WordCounterTest {

	@Test
	public void inputFileToQueue() {
		String file = "data/small.txt";
		Queue<String> expected = new Queue1L<String>();
		Queue<String> returned = WordCounter.fileToQueue(file);
		expected.enqueue("is purple.");
		expected.enqueue("Thomas. My favorite color");
		expected.enqueue("My name is Nikki ");
		assertEquals(returned, expected);
	}

	@Test
	public void wordsToSet() {
		Queue<String> expected = new Queue1L<String>();
		expected.enqueue("is purple.");
		expected.enqueue("Thomas. My favorite color");
		expected.enqueue("My name is Nikki ");
		Queue<String> compQ = new Queue1L<String>();
		compQ.enqueue("is purple.");
		compQ.enqueue("Thomas. My favorite color");
		compQ.enqueue("My name is Nikki ");
		Set<String> words = new Set1L<String>();
		Set<String> compSet = new Set1L<String>();
		compSet.add("is");
		compSet.add("purple");
		compSet.add("Thomas");
		compSet.add("My");
		compSet.add("favorite");
		compSet.add("color");
		compSet.add("name");
		compSet.add("Nikki");
		WordCounter.findEachUniqueWord(words, expected);
		assertEquals(expected, compQ);
		assertEquals(words, compSet);
	}
	
	@Test
	public void wordsToStack() {
		Queue<String> expected = new Queue1L<String>();
		expected.enqueue("is purple.");
		expected.enqueue("Thomas. My favorite color");
		expected.enqueue("My name is Nikki ");
		Queue<String> compQ = new Queue1L<String>();
		compQ.enqueue("is purple.");
		compQ.enqueue("Thomas. My favorite color");
		compQ.enqueue("My name is Nikki ");
		Stack<String> words = new Stack1L<String>();
		Stack<String> compSet = new Stack1L<String>();
		compSet.push("is");
		compSet.push("purple");
		compSet.push("Thomas");
		compSet.push("My");
		compSet.push("favorite");
		compSet.push("color");
		compSet.push("My");
		compSet.push("name");
		compSet.push("is");
		compSet.push("Nikki");
		WordCounter.findEachWord(expected, words);
		assertEquals(expected, compQ);
		assertEquals(words, compSet);
	}

	@Test
	public void wordscounter() {
		String word = "is";
		Stack<String> compSet = new Stack1L<String>();
		compSet.push("is");
		compSet.push("purple");
		compSet.push("Thomas");
		compSet.push("My");
		compSet.push("favorite");
		compSet.push("color");
		compSet.push("My");
		compSet.push("name");
		compSet.push("is");
		compSet.push("Nikki");
		int count = WordCounter.wordCounter(compSet, word);
		int expected = 2;
		assertEquals(expected, count);
	}

	@Test
	public void countMap() {
		Stack<String> compStack = new Stack1L<String>();
		compStack.push("is");
		compStack.push("purple");
		compStack.push("Thomas");
		compStack.push("My");
		compStack.push("favorite");
		compStack.push("color");
		compStack.push("My");
		compStack.push("name");
		compStack.push("is");
		compStack.push("Nikki");
		Set<String> compSet = new Set1L<String>();
		compSet.add("is");
		compSet.add("purple");
		compSet.add("Thomas");
		compSet.add("My");
		compSet.add("favorite");
		compSet.add("color");
		compSet.add("name");
		compSet.add("Nikki");
		Map<String, Integer> map = new Map1L<String, Integer>();
		Map<String, Integer> expected = new Map1L<String, Integer>();
		expected.add("is", 2);
		expected.add("purple", 1);
		expected.add("Thomas", 1);
		expected.add("My", 2);
		expected.add("favorite", 1);
		expected.add("color", 1);
		expected.add("name", 1);
		expected.add("Nikki", 1);
		WordCounter.wordAndNumberMap(compSet,compStack, map);
		assertEquals(expected, map);
	}

}
