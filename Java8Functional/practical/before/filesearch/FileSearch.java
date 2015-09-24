package filesearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class FileSearch {
	//Regular expression to match word boundaries (space & punctuation)
	private static final String WORD_REGEXP = "[- .:,;!?]+";
	
	/**
	 * Count the number of words that match supplied word in supplied file
	 * @param file			file to process (loaded as resource)
	 * @param wordToMatch	word to look for
	 * @return				number of matches
	 */
	public int countInstances(String file, String wordToMatch){
		int count = 0;
		final Pattern wordMatcher = Pattern.compile(WORD_REGEXP);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
			getClass().getResourceAsStream(file), StandardCharsets.UTF_8))) {
			String line = reader.readLine();
			while (line != null){
				String[] words = wordMatcher.split(line);
				for( String word : words){
					if (word.equals(wordToMatch)){
						++count;
					}
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * Count the number of words in supplied file
	 * @param file	file to process (loaded as resource)
	 * @return		total number of words
	 */
	public int countWords(String file){
		int count = 0;
		final Pattern wordMatcher = Pattern.compile(WORD_REGEXP);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
			getClass().getResourceAsStream(file), StandardCharsets.UTF_8))) {
			String line = reader.readLine();
			while (line != null){
				String[] words = wordMatcher.split(line);
				for( String word : words){
					//Make sure we only count words, not blank lines
					if(!word.isEmpty()){
						++ count;
					}
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * Count the number of different words in supplied file
	 * uses case-insensitive match.
	 * @param file	file to process (loaded as resource)
	 * @return		total number of different words
	 */
	public int countDistinctWords(String file){
		int count = 0;
		final Pattern wordMatcher = Pattern.compile(WORD_REGEXP);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
			getClass().getResourceAsStream(file), StandardCharsets.UTF_8))) {
			Set<String> distinctWords = new HashSet<>();
			String line = reader.readLine();
			while (line != null){
				String[] words = wordMatcher.split(line);
				for( String word : words){
					//Make sure we only count words, not blank lines
					if(!word.isEmpty()){
						distinctWords.add(word.toLowerCase());
					}
				}
				line = reader.readLine();
			}
			count = distinctWords.size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * Count the number of letters in the longest word in supplied file
	 * uses case-insensitive match.
	 * @param file	file to process (loaded as resource)
	 * @return		length of longest word
	 */
	public int longestWordLength(String file){
		int maxLength = 0;
		final Pattern wordMatcher = Pattern.compile(WORD_REGEXP);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
			getClass().getResourceAsStream(file), StandardCharsets.UTF_8))) {
			String line = reader.readLine();
			while (line != null){
				String[] words = wordMatcher.split(line);
				for( String word : words){
					if(word.length() > maxLength){
						maxLength = word.length();
					}
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxLength;
	}

	public static void main(String args[]){
		FileSearch fs = new FileSearch();
		System.out.println("number of words: " + fs.countWords("Ode.txt"));
		System.out.println("number of 'thy': " + fs.countInstances("Ode.txt", "thy"));
		System.out.println("number of distinct words: " + fs.countDistinctWords("Ode.txt"));
		System.out.println("length of longest word: " + fs.longestWordLength("Ode.txt"));
	}
}
