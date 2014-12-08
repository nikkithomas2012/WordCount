import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.stack.Stack;
import components.stack.Stack1L;

/**
 *This program counts word occurrences in a given input file and outputs
 *an HTML document with a table of the words and counts listed in
 *alphabetical order.
 *
 * @author Damonique Thomas
 *
 */
public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * Compare {@code String}s in lexicographic order.
     * (This method comes from my implementation of Project 10 from Software I)
     */
	    private static class StringLT implements Comparator<String> {
	        @Override
	        public int compare(final String o1, final String o2) {
	            return o1.compareTo(o2);
	        }
	    }

    /**
     * Generates the text in the inputed file{@code String} into a Queue
     * returns the Queue.
     *
     * @param file
     *            the string of the inputed file name
     * @return the Queue with the text from the inputed file name
     *
     */
    public static Queue<String> fileToQueue(final String file) {
    	SimpleReader input = new SimpleReader1L(file);
    	Queue<String> output = new Queue1L<String>();
    	while (!input.atEOS()) {
    		String next = input.nextLine();
    		output.enqueue(next);
    	}
    	output.flip();
    	input.close();
    	return output;
    }

    /**
     * Goes through each element in the inputed text {@code Queue}
     * and adds each unique word to the set {@code Set}.
     *
     * @param words
     *            the set of unique words in unique file
     * @param input
     * 				the Queue of lines of text from inputed file
     * @restores input
     * @replaces words
     *
     */
    public static void findEachUniqueWord(final Set<String> words,
    		final Queue<String> input) {
    	int l = input.length();
    	for (int i = 0; i < l; i++) {
    		String nextline = (String) input.dequeue();
    		String line = nextline.toLowerCase();
    		lineIntoSet(line, words);
    		input.enqueue(line);
    	}

    }

    /** Goes through each element in the inputed text {@code Queue}
    * and adds each word to the stack {@code Stack}.
    *
    * @param words
    *            the set of unique words in unique file
    * @param input
    * 				the Queue of lines of text from inputed file
    * @restores input
    * @replaces words
    *
    */
   public static void findEachWord(final Queue<String> input,
		   final Stack<String> words) {
   		int l = input.length();
   		for (int i = 0; i < l; i++) {
   			String nextline = (String) input.dequeue();
   			String line = nextline.toLowerCase();
   			lineIntoStack(line, words);
   			input.enqueue(line);
   		}
   }

   /**
    * Generates the set of characters in the given {@code String} into the
    * given {@code Set}.
    *
    * (This method comes from my implementation of Project 10 from Software I)
    *
    * @param str
    *            the given {@code String}
    * @param strSet
    *            the {@code Set} to be replaced
    * @replaces {@code strSet}
    * @ensures <pre>
    * {@code strSet = entries(str)}
    * </pre>
    */
   public static void generateElements(final String str,
		   final Set<Character> strSet) {
       assert str != null : "Violation of: str is not null";
       assert strSet != null : "Violation of: strSet is not null";
       int l = str.length();
   	//Adds each character in the separators string to a set
       for (int i = 0; i < l; i++) {
   		char x = str.charAt(i);
   		if (!strSet.contains(x)) {
   			strSet.add(x);
   		}
   	}
   }

   /** Outputs the table tags and table elements from {@code Map}.
   *
   * @param words
   *            the unique words in the inputed file
   * @param counts
   *            the map that contains each word and number of occurrences
   * @param out
   * 			the output stream
   * @restores words
   */
  public static void htmlTable(final Queue<String> words,
		  final Map<String, Integer> counts, final SimpleWriter out) {
	  assert out != null : "Violation of: out is not null";
      assert out.isOpen() : "Violation of: out.is_open";
      int l = words.length();
      for (int i = 0; i < l; i++) {
    	  String x = words.dequeue();
    	  int num = counts.value(x);
    	  out.println("<tr>");
    	  out.println("<td>" + x + "</td>");
    	  out.println("<td>" + num + "</td>");
    	  out.println("</tr>");
      }
  }

    /**
     * Generates the string of definitions{@code String} into a set.
     *
     *(Some of this method comes from my implementation of Project 10
     *		from Software I)
     *
     * @param line
     *            the given string of text from inputed file's Queue
     * @param words
     * 				the Set of words that are in the inputed file
     * @replaces words
     * @ensures <pre>
     * {@code Set = entries(line)}
     * </pre>
     */
    public static void lineIntoSet(final String line, final Set<String> words) {
    	//Generates Set of possible separators
    	final String separatorStr = " ,/\'().-";
        Set<Character> separatorSet = new Set1L<Character>();
        generateElements(separatorStr, separatorSet);
    	//Initiates position index to only add words to Set of words
    	int position = 0;
        while (position < line.length()) {
            String token = nextWordOrSeparator(line, position,
                    separatorSet);
            if (!separatorSet.contains(token.charAt(0))) {
                if (!words.contains(token)) {
                	words.add(token);
                }
            }
            position += token.length();
        }
    }

    /**
     * Generates the string of definitions{@code String} into a stack.
     *
     *(Some of this method comes from my implementation of Project 10
     *		from Software I)
     *
     * @param line
     *            the given string of text from inputed file's Queue
     * @param words
     * 				the Stack of words that are in the inputed file
     * @replaces words
     * @ensures <pre>
     * {@code Stack = entries(line)}
     * </pre>
     */
    public static void lineIntoStack(final String line,
    		final Stack<String> words) {
    	//Generates Set of possible separators
    	final String separatorStr = " ,/\'().-";
        Set<Character> separatorSet = new Set1L<Character>();
        generateElements(separatorStr, separatorSet);
    	//Initiates position index to only add words to Stack of words
    	int position = 0;
        while (position < line.length()) {
            String token = nextWordOrSeparator(line, position,
                    separatorSet);
            if (!separatorSet.contains(token.charAt(0))) {
                	words.push(token);
            }
            position += token.length();
        }
    }

    /**
     *
     * (This method comes from my implementation of Project 10 from Software I)
     *
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires <pre>
     * {@code 0 <= position < |text|}
     * </pre>
     * @ensures <pre>
     * {@code nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)}
     * </pre>
     */
    public static String nextWordOrSeparator(final String text,
    		final int position, final Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";
        //String to be updated and returned
        String str = "";
    	int l = text.length();
    	//checks if c is a separator or not
    	boolean c = separators.contains(text.charAt(position));
    	//if c is a separator, it adds each character after that
    	//until it reaches a non-separator
    	if (c) {
    		int end = position + 1;
    		while (c && end < l) {
    			c = separators.contains(text.charAt(end));
    			end++;
    		}
    		if (end - 1 == position) {
    			str = text.substring(position);
    		} else if (end == l && c) {
    			str = text.substring(position, end);
    		} else {
    			str = text.substring(position, end - 1);
    		}
    	//if c isn't a separator, it adds every character after that until it
    	//it reaches a separacter
    	} else {
    		int end = position + 1;
    		while (!c && end < l) {
    			c = separators.contains(text.charAt(end));
    			end++;
    		}
    		if ((end - 1 == position)) {
    			str = text.substring(position);
    		} else if (end == l && !c) {
    			str = text.substring(position, end);
    		} else {
    			str = text.substring(position, end - 1);
    		}
    	}
        return str;
    }

    /**
     * Outputs the closing tags in the generated HTML file.
     *
     * (This method comes from my implementation of Project 10 from Software I)
     * @param out
     *            the output stream
     * @updates {@code out.contents}
     * @requires <pre>
     * {@code out.is_open}
     * </pre>
     * @ensures <pre>
     * {@code out.content = #out.content * [the HTML closing tags]}
     * </pre>
     */
    private static void outputFooter(final SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Outputs the opening tags in the generated HTML file.
     *
     *(This method comes from my implementation of Project 10 from Software I)
     * @param out
     *            the output stream
     * @param file
     * 			the inputed name of the file
     * @updates {@code out.content}
     *
     * @ensures <pre>
     * {@code out.content = #out.content * [the HTML opening tags]}
     * </pre>
     */
    private static void outputHeader(final SimpleWriter out,
    		final String file) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        //Outputs header of HTML File
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Words Counted in " + file + "File</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Words Counted in " + file + "</h2>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Words</th>");
        out.println("<th>Counts</th>");
        out.println("</tr>");
    }

    /** Generates a Queue of Strings based off of the elements in the Set and
     * puts the Queue elements in lexicographic order.
    *
    * @param wordSet
    *            the unique words in the inputed file in a set
    * @param wordsQ
    *            the Queue that the unique words will be put into
    *  @param comp
    *            the Comparator to put the terms in alphabetical order
    *            {@code Comparator}
    * @replaces wordsQ
    * @restores words
    */
   public static void setToQueue(final Set<String> wordSet,
		   final Queue<String> wordsQ, final Comparator<String> comp) {
	   int l = wordSet.size();
	   Set<String> temp = wordSet.newInstance();
	   for (int i = 0; i < l; i++) {
		   String x = wordSet.removeAny();
		   temp.add(x);
		   wordsQ.enqueue(x);
	   }
	   wordSet.transferFrom(temp);
	   wordsQ.sort(comp);
   }

    /** Counts the number of times each word occurs in the inputed file
     * and adds the number with the word to a Map.
    *
    *
    * @param unique
    *            the Set of unique strings in inputed file
    * @param all
    *            the Stack of all the strings in inputed file
    * @param counts
    * 				the Map of unique words with the number of
    * 				occurrences
    * @replaces counts
    * @restores unique
    *
    */
   public static void wordAndNumberMap(final Set<String> unique,
		   final Stack<String> all, final Map<String, Integer> counts) {
     int l = unique.size();
     Set<String> temp = new Set1L<String>();
     for (int i = 0; i < l; i++) {
    	 String word = unique.removeAny();
    	 int count = wordCounter(all, word);
    	 counts.add(word, count);
    	 temp.add(word);
     }
     unique.transferFrom(temp);
   }

   /** Counts the number of times the word {@code String} is in inputed file.
   *
   *
   * @param all
   *            the Stack of all the strings in inputed file
   * @param word
   * 				the string whose number of occurrences is
   * 				being counted
   *@return the number of occurrences of the word {@code String}
   *@restores all
   */
  public static int wordCounter(final Stack<String> all, final String word) {
	  	int count = 0;
	  	int l = all.length();
	  	Stack<String> temp = new Stack1L<String>();
	  	for (int i = 0; i < l; i++) {
	  		String test = all.pop();
	  		if (test.equals(word)) {
	  			count++;
	  		}
	  		temp.push(test);
	  	}
	  	all.transferFrom(temp);
	  	return count;
  }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(final String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        //Asks user for file name
        out.println("Please enter the file name: ");
        String file = in.nextLine();
        //Puts text from file into a Queue
        Queue<String> text = fileToQueue(file);
        //Reads each element in Queue and adds each unique word to Set
        Set<String> uniqueWords = new Set1L<String>();
        findEachUniqueWord(uniqueWords, text);
        //Reads each element in Queue and adds EACH word to Stack
        Stack<String> allWords = new Stack1L<String>();
        findEachWord(text, allWords);
        //Counts the occurrence of each word & adds the word and number to a map
        Map<String, Integer> counts = new Map1L<String, Integer>();
        wordAndNumberMap(uniqueWords, allWords, counts);
        //Puts all unique words in Queue in lexicographic order
        Queue<String> unique = new Queue1L<String>();
        Comparator<String> cs = new StringLT();
        setToQueue(uniqueWords, unique, cs);
        //Creates HTML file and outputs header
        SimpleWriter html = new SimpleWriter1L("outputfile.html");
        outputHeader(html, file);
        htmlTable(unique, counts, html);
        //Outputs html footer to HTML file and closes open files
        outputFooter(html);
        html.close();
        in.close();
        out.close();
    }
}
