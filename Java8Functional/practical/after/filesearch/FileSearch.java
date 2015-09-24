package filesearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSearch {
	//Regular expression to match word boundaries (space & punctuation)
	private static final String WORD_REGEXP = "[- .:,;!?]+";
	
	/**
	 * Count the number of words that match supplied word in supplied file
	 * @param file		file to process (loaded as resource)
	 * @param wordToMatch	word to look for
	 * @return				number of matches
	 */
	public int countInstances(String file, String wordToMatch){
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
			getClass().getResourceAsStream(file), StandardCharsets.UTF_8))) {
			final Pattern wordMatcher = Pattern.compile(WORD_REGEXP);
			return (int) reader.lines()
					.flatMap(wordMatcher::splitAsStream)
					.filter(word -> Pattern.matches(wordToMatch, word))
					.count();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Count the number of words in supplied file
	 * @param file	file to process (loaded as resource)
	 * @return	total number of words
	 */
	public int countWords(String file){
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
			getClass().getResourceAsStream(file), StandardCharsets.UTF_8))) {
			final Pattern wordMatcher = Pattern.compile(WORD_REGEXP);
			return (int) reader.lines()
					.flatMap(wordMatcher::splitAsStream)
					.count();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Count the number of different words in supplied file
	 * uses case-insensitive match.
	 * @param file	file to process (loaded as resource)
	 * @return	total number of different words
	 */
	public int countDistinctWords(String file){
		return process(file, 
				(Stream<String> words)-> {
					return (int)(words.map(String::toLowerCase)
							.distinct()
							.count());});
	}

	/**
	 * Count the number of letters in the longest word in supplied file
	 * uses case-insensitive match.
	 * @param file	file to process (loaded as resource)
	 * @return	length of longest word
	 */	
	public int longestWordLength(String file){
		return process(file, 
				(Stream<String> words)-> {
					return words.collect(Collectors.summarizingInt(String::length))
						.getMax();});
	}
	
	//This method can be used to shorten the methods above, by extracting the repeating
	//behaviour, and taking in a function to deal with the specialised behaviour
	private int process(String file, Function<Stream<String>, Integer>wordProcessor ){
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
			getClass().getResourceAsStream(file), StandardCharsets.UTF_8))) {
			final Pattern wordMatcher = Pattern.compile(WORD_REGEXP);
			return wordProcessor.apply( reader.lines()
					.flatMap(wordMatcher::splitAsStream));
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void main(String args[]){
		FileSearch fs = new FileSearch();
		System.out.println("number of words: " + fs.countWords("Ode.txt"));
		System.out.println("number of 'thy': " + fs.countInstances("Ode.txt", "thy"));
		System.out.println("number of distinct words: " + fs.countDistinctWords("Ode.txt"));
		System.out.println("length of longest word: " + fs.longestWordLength("Ode.txt"));
	}
}
