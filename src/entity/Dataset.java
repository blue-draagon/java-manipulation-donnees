package entity;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
  private List<Word> wordList;
  private List<String> lines;
  private int lineNumber;
  private String type;

  public Dataset() {
  }

  public Dataset(String type) {
    this.wordList = new ArrayList<>();
    this.type = type;
  }

  public Dataset(List<String> lines, String type) {
    this.lines = lines;
    this.type = type;
    this.lineNumber = lines.size();
  }

  public List<Word> getWordList() {
    return wordList;
  }

  public void setWordList(List<Word> wordList) {
    this.wordList = wordList;
  }

  public void addWord(Word word) {
    this.wordList.add(word);
  }

  public List<String> getLines() {
    return lines;
  }

  public void setLines(List<String> lines) {
    this.lines = lines;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
